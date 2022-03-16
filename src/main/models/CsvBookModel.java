package main.models;

import java.util.Objects;
import java.util.Set;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;


public class CsvBookModel {
    @CsvBindByName
    private final String isbn;
    @CsvBindByName
    private final String title;
    @CsvBindAndSplitByName(elementType = String.class, splitOn = ", ")
    private final Set<String> authors;
    @CsvBindByName
    private final Integer year;
    @CsvBindByName
    private final String city;
    @CsvBindByName
    private final String publisher;
    @CsvBindByName
    private final Integer edition;

    public CsvBookModel() {
        this.title = null;
        this.authors = null;
        this.year = null;
        this.isbn = null;
        this.city = null;
        this.publisher = null;
        this.edition = null;
    }

    public String getTitle() {
        return Objects.requireNonNull(title);
    }

    public Set<String> getAuthors() {
        return Objects.requireNonNull(authors);
    }

    public Integer getYear() {
        return Objects.requireNonNull(year);
    }

    public String getIsbn() {
        return Objects.requireNonNull(isbn);
    }

    public String getCity() {
        return Objects.requireNonNull(city);
    }

    public String getPublisher() {
        return Objects.requireNonNull(publisher);
    }

    public Integer getEdition() {
        return Objects.requireNonNull(edition);
    }
}


