package main.library;

import java.util.Objects;
import java.util.Set;


/**
 * creates a book
 * @author Ahmad Al-Salameh (aa082)
 *
 */
public class Book {
    private final String isbn;
    private final String title;
    private final Set<String> authors;
    private final Integer year;
    private final String city;
    private final String publisher;
    private final Integer edition;

    public Book(String isbn, String title, Set<String> authors, Integer year, String city, String publisher,
                Integer edition) {
        this.title = Objects.requireNonNull(title);
        this.authors = Set.copyOf(Objects.requireNonNull(authors));
        this.year = Objects.requireNonNull(year);
        this.isbn = Objects.requireNonNull(isbn);
        this.city = Objects.requireNonNull(city);
        this.publisher = Objects.requireNonNull(publisher);
        this.edition = Objects.requireNonNull(edition);
    }

    /**
     * returns the title of the book
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * returns the author of the book
     * @return author
     */
    public Set<String> getAuthors() {
        return authors;
    }
    /**
     * returns the year of release of the book
     * @return year of release
     */
    public Integer getYear() {
        return year;
    }

    /**
     * returns the ISBN of the book
     * @return ISBN
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * returns the city where the book was published/written
     * @return city where the book was published/written
     */
    public String getCity() {
        return city;
    }

    /**
     * returns the publisher of the book
     * @return publisher
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * returns the edition of the book
     * @return edition
     */
    public Integer getEdition() {
        return edition;
    }
}

