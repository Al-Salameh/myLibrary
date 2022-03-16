package main.app;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import main.library.Book;
import main.library.BookCopy;
import main.library.Customer;
import main.library.Library;
import main.logic.Logic;



/**
 * allows the worker to navigate through the main.app.library system and to execute all
 * functions
 *
 * @author Ahmad Al-Salameh (aa082)
 *
 */




public class UserInterface {

    private final Library library;

    public UserInterface() {
        library = new Library();
    }

    public UserInterface(Library library) {
        Objects.requireNonNull(library);
        this.library = library;
    }

    /**
     * uses a StringBuilder to create a String with the given attributes of a book copy, for reporting
     *
     * @param the given book copy
     * @return the resulting String
     */
    private String bookString(final BookCopy book) {
        Objects.requireNonNull(book);
        var returnString = new StringBuilder();
        returnString.append(bookString(Logic.getBookViaISBN(book.getIsbn(), library)));
        returnString.append(book.getId());
        returnString.append(", ");
        returnString.append(book.isLent() ? "lent" : "not lent");
        returnString.append(", ");
        if (book.isLent()) {
            returnString.append("Lent on: " + book.getLentDate().toString());
            returnString.append(", ");
        }
        returnString.append(book.getShelfLocation());
        return returnString.toString();
    }

    /**
     * uses a StringBuilder to create a String with the given attributes of a book, for reporting
     *
     * @param the given book
     * @return the resulting String
     */
    private String bookString(final Book book) {
        Objects.requireNonNull(book);
        var returnString = new StringBuilder();
        returnString.append(book.getTitle());
        returnString.append(", ");
        returnString.append(book.getAuthors());
        returnString.append(", ");
        returnString.append(book.getYear());
        returnString.append(", ");
        returnString.append(book.getIsbn());
        return returnString.toString();
    }

    /**
     * uses a StringBuilder to create a String with the given attributes of a customer, for reporting
     *
     * @param the given customer
     * @return the resulting String
     */
    private String customerString(final Customer customer) {
        Objects.requireNonNull(customer);
        var returnString = new StringBuilder();
        returnString.append(customer.getId());
        returnString.append(", ");
        returnString.append(customer.getName());
        returnString.append(" ");
        returnString.append(customer.getFirstName());
        returnString.append(", ");
        returnString.append(customer.hasOverdraftFees());
        returnString.append(", ");
        returnString.append("Bookcopies lent: " + customer.getBookCopiesId().size());
        return returnString.toString();
    }

    /**
     * prints the main menu onto the console which is then used to navigate through the main functions of the software
     */
    private void printMenu() {
        System.out.println("--------------------------------------------------------------");
        System.out.println("Welcome to the main menu, press the corresponding number to select the desired menu item.");
        System.out.println("To exit please press 'q'");
        System.out.println("1. Search BookCopy via ISBN");
        System.out.println("2. Search BookCopy via title");
        System.out.println("3. Search BookCopy via author");
        System.out.println("4. Borrow BookCopy via customerID and bookID");
        System.out.println("5. Return BookCopy via customerID and bookID");
        System.out.println("6. Delete Customer via customerID");
        System.out.println("7. Delete Book via ISBN");
        System.out.println("8. Delete BookCopy via bookID");
        System.out.println("9. Reporting");
        System.out.println("10. Load Customer csv");
        System.out.println("11. Load BookCopies csv");
        System.out.println("12. Load Books csv");
        System.out.println("--------------------------------------------------------------");
    }

    /**
     * prints the sub menu onto the console which is then used to navigate through all the reporting features of the software
     */
    private void printSubMenu() {
        System.out.println("--------------------------------------------------------------");
        System.out.println("Welcome to the submenu! To leave the submenu press 'q'");
        System.out.println("1. List all Books");
        System.out.println("2. List all borrowed bookCopies");
        System.out.println("3. List all not borrowed bookCopies");
        System.out.println("4. List all customers");
        System.out.println("5. List all borrowed books by customers");
        System.out.println("6. List all publisher with the number of bookCopies and Percentage");
        System.out.println("--------------------------------------------------------------");
    }

    /**
     * prints the book copies from the stream
     *
     * @param supplier
     */
    public void printSetOfBookCopy(Supplier<Set<BookCopy>> supplier) {
        supplier.get().stream().forEach(book -> System.out.println(bookString(book)));
    }

    public void printSetOfBook(Supplier<Set<Book>> supplier) {
        supplier.get().stream().forEach(book -> System.out.println(bookString(book)));
    }

    /**
     * searches the library for all the book copies with the given ISBN, which the worker entered into the console, by using the scanner.
     * it then prints the matching book copies by using the method "printSetOfBookCopy".
     * @param scanner
     */
    public void searchBookCopyViaISBN(final Scanner scanner) {
        Objects.requireNonNull(scanner);
        System.out.println("Enter ISBN of book: ");
        final String isbn = scanner.next();
        printSetOfBookCopy(() -> Logic.searchViaISBN(isbn, library));
    }

    /**
     * searches the library for all the book copies with the given title, which the worker entered into the console, by using the scanner.
     * it then prints the matching book copies by using the method "printSetOfBookCopy".
     * @param scanner
     */
    public void searchBookCopyViaTitle(final Scanner scanner) {
        Objects.requireNonNull(scanner);
        System.out.println("Enter Title of book: ");
        final String titleBeginning = scanner.next();
        final String title = titleBeginning + scanner.nextLine();
        printSetOfBookCopy(() -> Logic.searchViaTitle(title, library));
    }

    /**
     * searches the library for all the book copies with the given author, which the worker entered into the console, by using the scanner.
     * it then prints the matching book copies by using the method "printSetOfBookCopy".
     *
     * @param scanner
     */
    public void searchBookCopyViaAuthor(final Scanner scanner) {
        Objects.requireNonNull(scanner);
        System.out.println("Enter Author of book: ");
        final String authorBeginning = scanner.next();
        final String author = authorBeginning + scanner.nextLine();
        printSetOfBookCopy(() -> Logic.searchViaAuthor(author, library));
    }

    /**
     * used to register the entered customer ID and book ID to then communicate them to the Method "lendBook" from the library class
     * so it can lend the given book to the customer
     *
     * @param scanner
     */
    public void borrowBookCopy(final Scanner scanner) {
        Objects.requireNonNull(scanner);
        System.out.println("Enter customerID");
        Long customerId = scanner.nextLong();
        System.out.println("Enter bookId");
        Long bookId = scanner.nextLong();
        library.lendBook(customerId, bookId);
    }

    /**
     * used to register the entered customer ID and book ID to then communicate them to the Method "returnBook" from the library class
     * so it can "return the book to the library"
     *
     * @param scanner
     */
    public void returnBookCopy(final Scanner scanner) {
        Objects.requireNonNull(scanner);
        System.out.println("Enter customerID");
        Long customerId = scanner.nextLong();
        System.out.println("Enter bookId");
        Long bookId = scanner.nextLong();
        library.returnBook(customerId, bookId);
    }

    /**
     * Creates a Set and List and uses those to calculate the percentage of how many book copies were published by which publisher via
     * iteration and prints that out
     * @return AllPublisherSet
     */
    public Set<String> printAllPublisherAndBookCopies() {
        Set<String> AllPublisherSet = new HashSet<String>();
        library.getBookCopies().stream()
                .forEach(book -> AllPublisherSet.add(Logic.getBookViaISBN(book.getIsbn(), library).getPublisher()));
        List<String> AllPublisherList = new ArrayList<String>(AllPublisherSet);
        int[] NumberOfBookCopies = new int[AllPublisherList.size()];
        for (BookCopy bookCopie : library.getBookCopies()) {
            for (int i = 0; i < AllPublisherList.size(); i++) {
                if (AllPublisherList.get(i).equals(Logic.getBookViaISBN(bookCopie.getIsbn(), library).getPublisher())) {
                    NumberOfBookCopies[i]++;
                }
            }
        }
        AllPublisherSet.clear();
        for (int i = 0; i < AllPublisherList.size(); i++) {
            double Percentage = (NumberOfBookCopies[i] * 100) / library.getBookCopies().size();
            AllPublisherSet.add(
                    AllPublisherList.get(i) + ":  " + NumberOfBookCopies[i] + " BookCopies " + "(" + Percentage + "%)");
        }
        return AllPublisherSet;

    }

    public static void main(String[] args) throws IOException {
        UserInterface ui = new UserInterface();
        Scanner scanner = new Scanner(System.in);
        Boolean userExit = false;

        do {
            ui.printMenu();
            String number = scanner.next();
            switch (number) {
                case "1":
                    try {
                        ui.searchBookCopyViaISBN(scanner);
                    } catch (RuntimeException e) {
                        System.err.println(e.getMessage());
                        break;
                    }
                    break;
                case "2":
                    try {
                        ui.searchBookCopyViaTitle(scanner);
                    } catch (RuntimeException e) {
                        System.err.println(e.getMessage());
                        break;
                    }
                    break;
                case "3":
                    try {
                        ui.searchBookCopyViaAuthor(scanner);
                    } catch (RuntimeException e) {
                        System.err.println(e.getMessage());
                        break;
                    }
                    break;
                case "4":
                    try {
                        ui.borrowBookCopy(scanner);
                    } catch (RuntimeException e) {
                        System.err.println(e.getMessage());
                        break;
                    }
                    break;
                case "5":
                    try {
                        ui.returnBookCopy(scanner);
                    } catch (RuntimeException e) {
                        System.err.println(e.getMessage());
                        break;
                    }
                    break;
                case "6":
                    System.out.println("Enter ID of Customer: ");
                    try {
                        ui.library.deleteCustomer(scanner.nextLong());
                    } catch (RuntimeException e) {
                        System.err.println(e.getMessage());
                        break;
                    }
                    break;
                case "7":
                    System.out.println("Enter ISBN of book: ");
                    try {
                        ui.library.deleteBook(scanner.next());
                    } catch (RuntimeException e) {
                        System.err.println(e.getMessage());
                        break;
                    }
                    break;
                case "8":
                    System.out.println("Enter ID of bookCopy: ");
                    try {
                        ui.library.deleteBookCopy(scanner.nextLong());
                    } catch (RuntimeException e) {
                        System.err.println(e.getMessage());
                        break;
                    }
                    break;
                case "9":
                    subMenu(scanner, ui);
                    break;
                case "10":
                    System.out.println("Path to customer.csv:");
                    Path customerCSV = Path.of(scanner.next());
                    try {
                        ui.library.csvCustomer(customerCSV);
                    } catch (IOException e) {
                        System.err.println("Csv file invalid");
                        break;
                    } catch(RuntimeException e) {
                        System.err.println(e.getMessage());
                        break;
                    }
                    break;
                case "11":
                    System.out.println("Path to bookCopies.csv:");
                    Path bookCopiesCsv = Path.of(scanner.next());
                    try {
                        ui.library.csvBookCopy(bookCopiesCsv);
                    } catch (IOException e) {
                        System.err.println("Csv file invalid");
                        break;
                    } catch(RuntimeException e) {
                        System.err.println(e.getMessage());
                        break;
                    }
                    break;
                case "12":
                    System.out.println("Path to books.csv:");
                    Path booksCsv = Path.of(scanner.next());
                    try {
                        ui.library.csvBook(booksCsv);
                    } catch (IOException e) {
                        System.err.println("Csv file invalid");
                        break;
                    } catch(RuntimeException e) {
                        System.err.println(e.getMessage());
                        break;
                    }
                    break;
                case "q":
                    userExit = true;
                    break;
                default:
                    System.err.println("try again");
                    break;
            }
        } while (!userExit);
        scanner.close();
    }

    /**
     * registers the entered number and executes the given function. used for all reporting purposes
     *
     * @param scanner
     * @param ui
     */
    public static void subMenu(final Scanner scanner, final UserInterface ui) {
        Objects.requireNonNull(scanner);
        Objects.requireNonNull(ui);
        Boolean userExit = false;

        do {
            ui.printSubMenu();
            String number = scanner.next();
            switch (number) {
                case "1":
                    ui.printSetOfBook(ui.library::getBooks);
                    break;
                case "2":
                    ui.printSetOfBookCopy(() -> ui.library.getBookCopies().stream().filter(book -> book.isLent())
                            .collect(Collectors.toUnmodifiableSet()));
                    break;
                case "3":
                    ui.printSetOfBookCopy(() -> ui.library.getBookCopies().stream().filter(book -> !book.isLent())
                            .collect(Collectors.toUnmodifiableSet()));
                    break;
                case "4":
                    ui.library.getCustomers().stream().forEach(customer -> System.out.println(ui.customerString(customer)));
                    break;
                case "5":
                    System.out.println("Enter customerID: ");
                    Long customerID = scanner.nextLong();
                    try {
                        ui.printSetOfBookCopy(() -> Logic.searchCustomerViaId(customerID, ui.library).getBookCopiesId()
                                .stream()
                                .map(id -> ui.library.getBookCopies().stream().filter(book -> book.getId().equals(id))
                                        .findAny().orElseThrow(() -> new RuntimeException("Book with id not found")))
                                .collect(Collectors.toSet()));
                    } catch (RuntimeException e) {
                        System.err.println(e.getMessage());
                        break;
                    }
                    break;
                case "6":
                    ui.printAllPublisherAndBookCopies().stream().forEach(String -> System.out.println(String));
                    ;
                    break;
                case "q":
                    userExit = true;
                    break;
                default:
                    System.err.println("try again");
                    break;
            }
        } while (!userExit);
    }
}
