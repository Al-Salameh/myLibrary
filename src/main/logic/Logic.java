package main.logic;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import main.library.Book;
import main.library.BookCopy;
import main.library.Customer;
import main.library.Library;


/**
 * implements some functions of the library
 *
 * @author Ahmad Al-Salameh (aa082)
 *
 */
public class Logic {

    public static Set<BookCopy> searchViaISBN(final String isbn, final Library library) {
        Objects.requireNonNull(isbn);
        Objects.requireNonNull(library);
        Set<BookCopy> setOfBookCopies = library.getBookCopies().stream().filter(book -> book.getIsbn().equals(isbn))
                .collect(Collectors.toUnmodifiableSet());
        if (setOfBookCopies.isEmpty()) {
            throw (new RuntimeException("ISBN not found"));
        }
        return setOfBookCopies;
    }
    /**
     * looks for book copies by ISBN using a filter and adds those found to a Set
     * @param book
     * @param library
     * @return setOfBookCopies
     */
    public static Set<BookCopy> findMatchingCopies(final Book book, final Library library) {
        Set<BookCopy> setOfBookCopies = library.getBookCopies().stream()
                .filter(bookCopy -> bookCopy.getIsbn().equals(book.getIsbn())).collect(Collectors.toUnmodifiableSet());
        if (setOfBookCopies.isEmpty()) {
            throw (new RuntimeException("Copies not found"));
        }
        return setOfBookCopies;
    }

    /**
     * searches a book copy via title using a filter
     * @param title of the book
     * @param library
     * @return book copy
     */
    public static Set<BookCopy> searchViaTitle(final String title, final Library library) {
        Objects.requireNonNull(title);
        Objects.requireNonNull(library);
        Book foundBook = library.getBooks().stream().filter(book -> book.getTitle().contentEquals(title)).findAny()
                .orElseThrow(() -> new RuntimeException("Book with title not found"));
        return findMatchingCopies(foundBook, library);
    }

    /**
     * searches a book copy via author using a filter
     * @param author of the book
     * @param library
     * @return book copy
     */
    public static Set<BookCopy> searchViaAuthor(final String author, final Library library) {
        Objects.requireNonNull(author);
        Objects.requireNonNull(library);
        library.getBooks().stream().filter(book -> book.getAuthors().contains(author)).findAny()
                .orElseThrow(() -> new RuntimeException("Book with author not found"));
        return library.getBooks().stream().filter(book -> book.getAuthors().contains(author))
                .map(book -> findMatchingCopies(book, library)).flatMap(Set::stream).collect(Collectors.toSet());
    }

    /**
     * searches a customer via id using a filter
     * @param customerId
     * @param library
     * @return customer
     */
    public static Customer searchCustomerViaId(final Long customerId, Library library) {
        Objects.requireNonNull(customerId);
        Objects.requireNonNull(library);
        return library.getCustomers().stream().filter(customer -> customer.getId().equals(customerId)).findAny()
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    /**
     * looks for a book via its ISBN by comparing the given ISBN with the ISBN of a stream of books
     * if it doesn't any it throws a RuntimeException
     * @param isbn
     * @param library
     * @throws RuntimeException
     */
    public static Book getBookViaISBN(final String isbn, Library library) {
        Objects.requireNonNull(isbn);
        Objects.requireNonNull(library);
        return library.getBooks().stream().filter(book -> book.getIsbn().equals(isbn)).findAny()
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

}
