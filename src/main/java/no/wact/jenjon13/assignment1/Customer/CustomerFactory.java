package no.wact.jenjon13.assignment1.customer;

/**
 * A static factory class for Customer instances.
 */
public class CustomerFactory {

    private static final int CUSTOMER_MAX_AMOUNT = 10;
    private static int customerCount = 0;

    /**
     * Static method for returning a new Customer instance with the provided customerName set.
     * @param customerName A name String that will function as the Customer instance's identifier
     * @return A Customer instance.
     */
    public static Customer newCustomer(final String customerName) {
        return customerCount++ < CUSTOMER_MAX_AMOUNT ? new CarCustomer(customerName) : null;
    }
}
