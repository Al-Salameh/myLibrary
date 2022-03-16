package main.library;

import java.time.LocalDate;
import java.util.Objects;

/**
        * creates a book copy
        *
        * @author Ahmad Al-Salameh (aa082)
        *
        */
public class BookCopy {
    private final Long id;
    private final String isbn;
    private final String shelfLocation;
    private final LocalDate addedToLibrary;
    private final Boolean lent;
    private final LocalDate lentDate;

    public BookCopy(Long id, String isbn, String shelfLocation, LocalDate addedToLibrary, Boolean lent,
                    LocalDate lentDate) {
        this.id = Objects.requireNonNull(id);
        this.isbn = Objects.requireNonNull(isbn);
        this.shelfLocation = Objects.requireNonNull(shelfLocation);
        this.addedToLibrary = Objects.requireNonNull(addedToLibrary);
        this.lent = Objects.requireNonNull(lent);
        this.lentDate = Objects.requireNonNull(lentDate);
    }

    /**
     * returns the id of the book copy
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * returns the book which is superior to the book copy
     *
     * @return book
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * returns the location of the book copy
     *
     * @return location
     */
    public String getShelfLocation() {
        return shelfLocation;
    }

    /**
     * returns the date at which the book was added to the library
     *
     * @return date at which the book was added
     */
    public LocalDate getAddedToLibrary() {
        return addedToLibrary;
    }

    /**
     * returns true/false if the the copy is/is not lend
     *
     * @return true/false
     */
    public Boolean isLent() {
        return lent;
    }

    /**
     * returns the lending date of the book copy
     *
     * @return lending date
     */
    public LocalDate getLentDate() {
        return lentDate;
    }

}
