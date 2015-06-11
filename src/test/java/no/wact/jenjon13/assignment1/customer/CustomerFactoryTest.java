package no.wact.jenjon13.assignment1.customer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CustomerFactoryTest {
    private final String customerName = "TEST";

    @Test
    public void testNewCustomer() throws Exception {
        final Customer testCustomer = CustomerFactory.newCustomer(customerName);
        final CarCustomer carCustomer = new CarCustomer(customerName);
        assertEquals("The factory- and manually created instances should be considered equal.",
                carCustomer, testCustomer);
    }

    @Test
    public void testDoesNotCreateMoreThanAllowedCustomers() {
        final Customer[] customers = new Customer[CustomerFactory.CUSTOMER_MAX_AMOUNT];
        for (int i = 0; i < CustomerFactory.CUSTOMER_MAX_AMOUNT; i++) {
            customers[i] = CustomerFactory.newCustomer(String.valueOf(i));
        }

        assertNull("Any requests post exceeding customer limit should return null",
                CustomerFactory.newCustomer(customerName));
    }
}