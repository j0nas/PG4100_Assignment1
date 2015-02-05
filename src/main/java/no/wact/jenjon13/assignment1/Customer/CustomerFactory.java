package no.wact.jenjon13.assignment1.customer;

import no.wact.jenjon13.assignment1.leaser.CarLeaser;

public class CustomerFactory {

    private static final int CUSTOMER_MAX_AMOUNT = 10;
    private static int customerCount = 0;

    public static Customer newCustomer(final String customerName, final CarLeaser leaser) {
        return customerCount++ < CUSTOMER_MAX_AMOUNT ? new CarCustomer(customerName, leaser) : null;
    }
}
