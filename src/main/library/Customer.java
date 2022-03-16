package main.library;

import java.util.Objects;
import java.util.Set;

/**
 * creates a customer
 *
 * @author Ahmad Al-Salameh (aa082)
 *
 */
public class Customer {
    private final Long id;
    private final String name;
    private final String firstName;
    private final String address;
    private final String zipCode;
    private final String city;
    private final Boolean hasOverdraftFees;
    private final Boolean hasPaidMonthlyContribution;
    private final Set<Long> bookCopiesId;

    public Customer(Long id, String name, String firstName, String address, String city, String zipCode,
                    Boolean hasOverdraftFees, Boolean hasPaidMonthlyContribution, Set<Long> bookCopiesId) {
        this.id = Objects.requireNonNull(id);
        this.firstName = Objects.requireNonNull(firstName);
        this.name = Objects.requireNonNull(name);
        this.hasOverdraftFees = Objects.requireNonNull(hasOverdraftFees);
        this.city = Objects.requireNonNull(city);
        this.zipCode = Objects.requireNonNull(zipCode);
        this.address = Objects.requireNonNull(address);
        this.bookCopiesId = Set.copyOf(Objects.requireNonNull(bookCopiesId));
        this.hasPaidMonthlyContribution = Objects.requireNonNull(hasPaidMonthlyContribution);
    }

    /**
     * returns the id of the customer
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * returns the first name of the customer
     *
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * returns the name of the customer
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * returns true/false if the fees are/are not paid
     *
     * @return true/false
     */
    public boolean hasOverdraftFees() {
        return hasOverdraftFees;
    }

    /**
     * returns the city of the customer
     *
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     * returns the zip code of the customer
     *
     * @return zip code
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * returns the address of the customer
     *
     * @return address
     */
    public String getAddress() {
        return address;
    }

    public boolean hasPaidMonthlyContribution() {
        return hasPaidMonthlyContribution;
    }

    /**
     * returns the lended book copies of the customer
     *
     * @return lended book copies
     */
    public Set<Long> getBookCopiesId() {
        return bookCopiesId;
    }
}
