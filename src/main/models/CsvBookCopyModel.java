package main.models;

import java.time.LocalDate;
import java.util.Objects;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

public class CsvBookCopyModel {
    @CsvBindByName
    private final Long id;
    @CsvBindByName
    private final String bookIsbn;
    @CsvBindByName
    private final String shelfLocation;
    @CsvBindByName
    @CsvDate("yyyy-MM-dd")
    private final LocalDate addedToLibrary;
    @CsvBindByName
    private final Boolean lent;
    @CsvBindByName
    @CsvDate("yyyy-MM-dd")
    private final LocalDate lentDate;

    public CsvBookCopyModel() {
        this.id = null;
        this.bookIsbn = null;
        this.shelfLocation = null;
        this.addedToLibrary = null;
        this.lent = null;
        this.lentDate = null;
    }


    public Long getId() {
        return Objects.requireNonNull(id);
    }

    public String getBookIsbn() {
        return Objects.requireNonNull(bookIsbn);
    }

    public String getShelfLocation() {
        return Objects.requireNonNull(shelfLocation);
    }

    public LocalDate getAddedToLibrary() {
        return Objects.requireNonNull(addedToLibrary);
    }

    public Boolean getLent() {
        return Objects.requireNonNull(lent);
    }

    public LocalDate getLentDate() {
        return Objects.requireNonNull(lentDate);
    }

}




