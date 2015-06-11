package no.wact.jenjon13.assignment1.customer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CarCustomerTest {
    private final String customerName = "TEST";
    Customer customer = null;

    @Before
    public void setUp() throws Exception {
        customer = new CarCustomer(customerName);
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("The getName method should return the expected value.",
                customerName, customer.getName());
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("The toString method should return the expected value.",
                "Customer{customerName='" + customerName + "'}", customer.toString());
    }
}
