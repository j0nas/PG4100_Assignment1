package no.wact.jenjon13.assignment1.customer;

/**
 * Abstract value object representing a customer.
 */
public abstract class Customer implements Runnable, Comparable {
    protected final String customerName;

    /**
     * Constructor for the class.
     * @param customerName
     */
    public Customer(final String customerName) {
        this.customerName = customerName;
    }

    /**
     * Getter for the customerName field.
     * @return The value held by the customerName field.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * equals() override in order to make this class a value object.
     * @param o The object which the instance is to be compared against.
     * @return True if the comparing objects match in value, false otherwise.
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Customer customer = (Customer) o;
        return !(customerName != null ? !customerName.equals(customer.customerName) : customer.customerName != null);
    }

    /**
     * hashCode() override to go with the equals() override.
     * @return The hashcode of the customerName field.
     */
    @Override
    public int hashCode() {
        return customerName != null ? customerName.hashCode() : 0;
    }

    /**
     * compareTo method override in accordance with the Comparable interface.
     * Used in order to enable enqueuing of Customer instances.
     * @param o The instance to which this this instance is to be compared to, using the equals implementation.
     * @return -1, 0 or 1 if the comparing object is not equal, equal or greater than this instance, respectively.
     */
    @Override
    public int compareTo(final Object o) {
        return (o instanceof Customer) ? -1 : ((Customer) o).equals(o) ? 0 : 1;
    }
}
