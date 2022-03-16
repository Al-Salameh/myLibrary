package main.models;

import java.util.Objects;
import java.util.Set;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;

public class CsvCustomerModel {
    @CsvBindByName
    private final Long id;
    @CsvBindByName
    private final String name;
    @CsvBindByName
    private final String firstName;
    @CsvBindByName
    private final String address;
    @CsvBindByName
    private final String zipCode;
    @CsvBindByName
    private final String city;
    @CsvBindByName
    private final Boolean hasOverdraftFees;
    @CsvBindByName
    private final Boolean hasPaidMonthlyContribution;
    @CsvBindAndSplitByName(elementType = Long.class, splitOn = ",")
    private final Set<Long> bookIds;

    public CsvCustomerModel() {
        this.id = null;
        this.name = null;
        this.firstName = null;
        this.address = null;
        this.zipCode = null;
        this.city = null;
        this.hasOverdraftFees = null;
        this.hasPaidMonthlyContribution = null;
        this.bookIds = Set.of();
    }

    public Long getId() {
        return Objects.requireNonNull(id);
    }

    public String getFirstName() {
        return Objects.requireNonNull(firstName);
    }

    public String getName() {
        return Objects.requireNonNull(name);
    }

    public boolean hasOverdraftFees() {
        return Objects.requireNonNull(hasOverdraftFees);
    }

    public String getCity() {
        return Objects.requireNonNull(city);
    }

    public String getZipCode() {
        return Objects.requireNonNull(zipCode);
    }

    public String getAddress() {
        return Objects.requireNonNull(address);
    }

    public boolean hasPaidMonthlyContribution() {
        return Objects.requireNonNull(hasPaidMonthlyContribution);
    }

    public Set<Long> getBookIds()
    {
        if(bookIds == null)
            return Set.of();
        if(bookIds.contains(null))
            return Set.of();
        return bookIds;
    }
}
