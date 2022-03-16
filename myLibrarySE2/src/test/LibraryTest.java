package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.app.UserInterface;
import main.library.Book;
import main.library.BookCopy;
import main.library.Customer;
import main.library.Library;

public class LibraryTest {
    Library library;
    UserInterface ui;

    @BeforeEach
    void init() {
        // Long id, String name, String firstName, String address, String city, String
        // zipCode,
        // Boolean hasOverdraftFees, Boolean hasPaidMonthlyContribution, Set<BookCopy>
        // bookCopies
        Book book = new Book("100", "The Life", Set.of(), 2011, "Stuttgart", "Klett", 12);
        Book book2 = new Book("101", "The Life2", Set.of(), 2011, "Stuttgart", "Penguin Publishing", 12);
        BookCopy bookCopy1 = new BookCopy(0L, "100", "2a", LocalDate.now(), false, LocalDate.now());
        BookCopy bookCopy2 = new BookCopy(1L, "100", "3a", LocalDate.now(), true, LocalDate.now());
        BookCopy bookCopy3 = new BookCopy(2L, "101", "3a", LocalDate.now(), false, LocalDate.now());
        Customer c1 = new Customer(123L, "Paul", "Hund", "Böblingen", "HauptStrasse", "70563", true, true, Set.of(1L));
        Customer c2 = new Customer(1234L, "Alex", "Hase", "Böblingen", "HauptStrasse", "70563", true, true, Set.of());
        Customer c3 = new Customer(12345L, "Alex", "Hase", "Böblingen", "HauptStrasse", "70563", false, false,
                Set.of());
        Set<BookCopy> bookCopies = new HashSet<BookCopy>();
        Set<Customer> customers = new HashSet<Customer>();
        library = new Library(Set.of(bookCopy1, bookCopy2, bookCopy3), Set.of(c1, c2, c3), Set.of(book, book2));
        ui = new UserInterface(library);

    }

    @Test
    void testDeleteBookViaISBN() {
        assertThrows(RuntimeException.class, () -> library.deleteBook("100"));
        library.deleteBook("101");
        assertEquals(2, library.getBookCopies().size());
        assertThrows(RuntimeException.class, () -> library.deleteBook("101"));
    }

    @Test
    void testDeleteBookViaID() {
        library.deleteBookCopy(0L);
        assertEquals(2, library.getBookCopies().size());
        assertThrows(RuntimeException.class, () -> library.deleteBookCopy(0L));
        assertThrows(RuntimeException.class, () -> library.deleteBookCopy(1L));
    }

    @Test
    void testDeleteCustomer() {
        assertThrows(RuntimeException.class, () -> library.deleteCustomer(123L));
        assertThrows(RuntimeException.class, () -> library.deleteCustomer(12345L));
        library.deleteCustomer(1234L);
        assertEquals(2, library.getCustomers().size());
        assertThrows(RuntimeException.class, () -> library.deleteCustomer(1234L));
    }

    @Test
    void testPrintAllPublisherAndBookCopies() {
        String publisher1 = "Klett:  2 BookCopies (66.0%)";
        String publisher2 = "Penguin Publishing:  1 BookCopies (33.0%)";
        Set<String> publisherSet = ui.printAllPublisherAndBookCopies();
        Iterator<String> it = publisherSet.iterator();
        assertEquals(it.next(), publisher1);
        assertEquals(it.next(), publisher2);
        assertEquals(publisherSet.size(), 2);

    }

}