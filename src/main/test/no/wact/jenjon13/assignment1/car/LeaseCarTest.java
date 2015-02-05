package no.wact.jenjon13.assignment1.car;

import no.wact.jenjon13.assignment1.customer.Customer;
import no.wact.jenjon13.assignment1.customer.CustomerFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the LeaseCar class.
 */
public class LeaseCarTest {
    private LeaseCar car;
    private Customer customer;

    @Before
    public void setUp() throws Exception {
        car = CarFactory.newCar();
        customer = CustomerFactory.newCustomer("TestCustomer");
    }

    @Test
    public void testGetLeasedTimes() throws Exception {
        assertEquals("The leasedTimes field should return zero prior to initial lease.", 0, car.getLeasedTimes());

        car.setLeased(true);
        assertEquals("The leasedTimes field should return an incremented value post first lease.", 1, car.getLeasedTimes());

        car.setLeased(false);
        assertEquals("The leasedTimes field should remain the same after ending first lease.", 1, car.getLeasedTimes());
    }

    @Test
    public void testGetLeasedBy() throws Exception {

    }

    @Test
    public void testSetLeasedBy() throws Exception {

    }

    @Test
    public void testGetRegistrationNumber() throws Exception {

    }

    @Test
    public void testIsLeased() throws Exception {

    }

    @Test
    public void testSetLeased() throws Exception {

    }

    @Test
    public void testEquals() throws Exception {

    }

    @Test
    public void testHashCode() throws Exception {

    }
}