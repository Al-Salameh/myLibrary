package main.library;

import main.models.*;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

/**
 * implements the functions of the library
 *
 * @author Ahmad Al-Salameh (aa082)
 *
 */
public class Library {
    private final Set<BookCopy> bookCopies;
    private final Set<Customer> customers;
    private final Set<Book> books;

    public Library(Set<BookCopy> bookCopies, Set<Customer> customers, Set<Book> books) {
        this.bookCopies = new HashSet<>(Objects.requireNonNull(bookCopies));
        this.customers = new HashSet<>(Objects.requireNonNull(customers));
        this.books = new HashSet<>(Objects.requireNonNull(books));
    }

    public Library() {
        this(Set.of(), Set.of(), Set.of());
    }

    private Book convert(CsvBookModel m) {
        Objects.requireNonNull(m);
        if (books.stream().anyMatch(book -> book.getIsbn().contentEquals(m.getIsbn())))
            throw new RuntimeException("Book with isbn already found");
        return new Book(m.getIsbn(), m.getTitle(), m.getAuthors(), m.getYear(), m.getCity(), m.getPublisher(),
                m.getEdition());
    }

    private BookCopy convert(CsvBookCopyModel m) {
        Objects.requireNonNull(m);
        if (!books.stream().anyMatch(book -> book.getIsbn().contentEquals(m.getBookIsbn())))
            throw new RuntimeException("Book with given isbn not found when importing BookCopy");
        return new BookCopy(m.getId(), m.getBookIsbn(), m.getShelfLocation(), m.getAddedToLibrary(), m.getLent(),
                m.getLentDate());
    }

    private Customer convert(CsvCustomerModel m) {
        Objects.requireNonNull(m);
        if (customers.stream().anyMatch(customer -> customer.getId().equals(m.getId())))
            throw new RuntimeException("Customer with id already found when importing Customer");
        if (m.getBookIds().size() > 5)
            throw new RuntimeException("Customer has more than 5 books when import Customer");
        // Check if all of the bookIds are valid and lent
        m.getBookIds().forEach(id -> {
            if (!bookCopies.stream().anyMatch(book -> book.getId().equals(id) && book.isLent()))
                throw new RuntimeException("Book couldn't be found or is not lent");
        });
        // Check if any other user has the same books lent
        m.getBookIds().forEach(id -> {
            if (customers.stream().anyMatch(customer -> customer.getBookCopiesId().contains(id)))
                throw new RuntimeException("Another customer has the book already lent");
        });

        return new Customer(m.getId(), m.getName(), m.getFirstName(), m.getAddress(), m.getCity(), m.getZipCode(),
                m.hasOverdraftFees(), m.hasPaidMonthlyContribution(), m.getBookIds());

    }
    /**
     * uses a reader to register the path to the CSV which contains all the information about the books which are being imported.
     * The reader then uses the book model to add all the imported books via iteration
     * @param pathToBook
     * @throws IOException
     */
    public void csvBook(Path pathToBook) throws IOException {
        Objects.requireNonNull(pathToBook);
        try (Reader reader = Files.newBufferedReader(pathToBook)) {
            CsvToBean<CsvBookModel> booksCsvBean = new CsvToBeanBuilder<CsvBookModel>(reader)
                    .withType(CsvBookModel.class).withIgnoreLeadingWhiteSpace(true).withSeparator(';').build();
            Iterator<CsvBookModel> bookIterator = booksCsvBean.iterator();
            while (bookIterator.hasNext())
                books.add(convert(bookIterator.next()));
        }
    }
    /**
     * uses a reader to register the path to the CSV which contains all the information about the book copies which are being imported.
     * The reader then uses the book copy model to add all the imported books via iteration
     * @param pathToBookCopy
     * @throws IOException
     */
    public void csvBookCopy(Path pathToBookCopy) throws IOException {
        Objects.requireNonNull(pathToBookCopy);
        try (Reader reader = Files.newBufferedReader(pathToBookCopy)) {
            CsvToBean<CsvBookCopyModel> bookCopiesBean = new CsvToBeanBuilder<CsvBookCopyModel>(reader)
                    .withType(CsvBookCopyModel.class).withIgnoreLeadingWhiteSpace(true).withSeparator(';').build();
            Iterator<CsvBookCopyModel> bookCopiesIterator = bookCopiesBean.iterator();
            while (bookCopiesIterator.hasNext())
                bookCopies.add(convert(bookCopiesIterator.next()));
        }
    }

    /**
     * uses a reader to register the path to the CSV which contains all the information about the book customers which are being imported.
     * The reader then uses the book customer to add all the imported books via iteration
     * @param pathToBookCopy
     * @throws IOException
     */
    public void csvCustomer(Path pathToCustomer) throws IOException {
        Objects.requireNonNull(pathToCustomer);
        try (Reader reader = Files.newBufferedReader(pathToCustomer)) {
            CsvToBean<CsvCustomerModel> customersCsvBean = new CsvToBeanBuilder<CsvCustomerModel>(reader)
                    .withType(CsvCustomerModel.class).withIgnoreLeadingWhiteSpace(true).withSeparator(';').build();
            Iterator<CsvCustomerModel> customerIterator = customersCsvBean.iterator();
            while (customerIterator.hasNext())
                customers.add(convert(customerIterator.next()));
        }
    }

    /**
     * returns the set of all existing book copies
     *
     * @return set of book copies
     */
    public Set<BookCopy> getBookCopies() {
        return Set.copyOf(bookCopies);
    }

    /**
     * returns the set of customers
     *
     * @return set of customers
     */
    public Set<Customer> getCustomers() {
        return Set.copyOf(customers);
    }

    public Set<Book> getBooks() {
        return Set.copyOf(books);
    }

    /**
     * Firstly the method checks whether the given copy is already lent or not.
     * If not a new object, the "new" book copy with updated values for shelf-location and "isLent" etc. will be created, also a "new" customer
     * with altered values of the significant attributes will be created. The old book copy and customer will be removed and the new ones
     * will be added. Therefore the book copy has been lent.
     *
     * @param customer id
     * @param book     id
     * @throws RuntimeException
     */
    public void lendBook(final Long customerId, final Long bookId) {
        Objects.requireNonNull(customerId);
        Objects.requireNonNull(bookId);
        Customer customer = getCustomer(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        BookCopy bookCopy = getBookCopy(bookId).orElseThrow(() -> new RuntimeException("BookCopy not found"));
        if (bookCopy.isLent())
            throw new RuntimeException("Book is already lent");

        // Remove customer with outdated data
        customers.remove(customer);
        // Book copy gets updated data
        Set<Long> copiesId = new HashSet<>(customer.getBookCopiesId());
        var updatedBook = new BookCopy(bookCopy.getId(), bookCopy.getIsbn(), bookCopy.getShelfLocation(),
                bookCopy.getAddedToLibrary(), true, LocalDate.now());
        copiesId.add(updatedBook.getId());

        customers.add(new Customer(customer.getId(), customer.getName(), customer.getFirstName(), customer.getAddress(),
                customer.getZipCode(), customer.getCity(), customer.hasOverdraftFees(),
                customer.hasPaidMonthlyContribution(), copiesId));

        // "Old" book copy and customer get removed
        bookCopies.remove(bookCopy);
        bookCopies.add(updatedBook);

    }

    /**
     * Firstly the method checks whether the given copy is already lent or not.
     * If not a new object, the "new" book copy with updated values for shelf-location and "isLent" etc. will be created, also a "new" customer
     * with altered values of the significant attributes will be created. The old book copy and customer will be removed and the new ones
     * will be added. therefore the book copy has been returned.
     *
     * @param customer id
     * @param book     id
     * @throws RuntimeException
     */
    public void returnBook(final Long customerId, final Long bookId) {
        Objects.requireNonNull(customerId);
        Objects.requireNonNull(bookId);
        Customer customer = getCustomer(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        BookCopy bookCopy = getBookCopy(bookId).orElseThrow(() -> new RuntimeException("BookCopy not found"));
        if (!bookCopy.isLent())
            throw new RuntimeException("Book is not lent");
        if (!customer.getBookCopiesId().contains(bookCopy.getId()))
            throw new RuntimeException("This customer doesnt have the book");
        // Remove copy with outdated data
        bookCopies.remove(bookCopy);
        BookCopy updatedBook = new BookCopy(bookCopy.getId(), bookCopy.getIsbn(), bookCopy.getShelfLocation(),
                bookCopy.getAddedToLibrary(), false, bookCopy.getLentDate());
        // Old customer gets removed, new customer and copy added
        bookCopies.add(updatedBook);
        customers.remove(customer);
        Set<Long> copies = new HashSet<>(customer.getBookCopiesId());
        copies.remove(bookCopy.getId());
        customers.add(new Customer(customer.getId(), customer.getName(), customer.getFirstName(), customer.getAddress(),
                customer.getZipCode(), customer.getCity(), customer.hasOverdraftFees(),
                customer.hasPaidMonthlyContribution(), copies));
    }

    /**
     * searches for a customer with a given ID and returns that customer
     *
     * @param customer id
     * @return customer
     */
    private Optional<Customer> getCustomer(final Long ID) {
        Objects.requireNonNull(ID);
        return customers.stream().filter(c -> c.getId().equals(ID)).findAny();
    }

    /**
     * searches for a book copy with a given ID and returns that copy
     *
     * @param book copy id
     * @return book copy
     */
    private Optional<BookCopy> getBookCopy(final Long ID) {
        Objects.requireNonNull(ID);
        return bookCopies.stream().filter(book -> book.getId().equals(ID)).findAny();
    }

    /**
     * Checks if the customer has unpaid fees or books that have yet to be returned. If not the given customer will be removed
     *
     * throws RuntimeException
     * @param customer id
     */
    public void deleteCustomer(final Long ID) {
        Objects.requireNonNull(ID);
        Customer customer = getCustomer(ID).orElseThrow(() -> new RuntimeException("Customer with id not found"));
        if (!customer.hasOverdraftFees())
            throw new RuntimeException("Fee not paid");
        if (!customer.getBookCopiesId().isEmpty())
            throw new RuntimeException("Customer has books");
        customers.remove(customer);
    }

    /**
     * Checks whether a book copy with that ID exists of whether that book copy is lent. If everything checks out the book copy will be deleted
     * @throws RuntimeException
     * @param book copy id
     */
    public void deleteBookCopy(final Long ID) {
        Objects.requireNonNull(ID);
        Optional<BookCopy> bookCopy = getBookCopy(ID);
        if (bookCopy.isEmpty())
            throw new RuntimeException("ID not found");
        if (bookCopy.get().isLent())
            throw new RuntimeException("Book is lent");
        bookCopies.remove(bookCopy.get());
    }

    /**
     * Checks whether the book exists in the library and then looks for a book with the given ISBN. If a book is found then it will be deleted.
     *
     * @param isbn
     */
    public void deleteBook(final String isbn) {
        Objects.requireNonNull(isbn);
        // Check if book is in library
        Book foundBook = books.stream().filter(book -> book.getIsbn().equals(isbn)).findAny()
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Collect hardcopies
        Set<BookCopy> copies = bookCopies.stream().filter(book -> book.getIsbn().equals(isbn))
                .collect(Collectors.toSet());
        if (copies.isEmpty())
            throw new RuntimeException("No BookCopy found with given isbn");

        if (copies.stream().anyMatch(BookCopy::isLent))
            throw new RuntimeException("At least one book is lent");
        // Deletes books
        bookCopies.removeAll(copies);
        books.remove(foundBook);
    }

}
