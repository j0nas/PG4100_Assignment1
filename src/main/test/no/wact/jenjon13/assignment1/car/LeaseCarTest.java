package no.wact.jenjon13.assignment1.car;

import no.wact.jenjon13.assignment1.customer.Customer;
import no.wact.jenjon13.assignment1.customer.CustomerFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
        assertEquals("The leasedTimes field should return zero prior to initial lease.",
                0, car.getLeasedTimes());

        car.setLeased(true);
        assertEquals("The leasedTimes field should return an incremented value post first lease.",
                1, car.getLeasedTimes());

        car.setLeased(false);
        assertEquals("The leasedTimes field should remain the same after ending first lease.",
                1, car.getLeasedTimes());
    }

    @Test
    public void testGetSetLeasedBy() throws Exception {
        assertEquals("The leasedBy field should return zero prior to initial lease.",
                null, car.getLeasedBy());

        car.setLeasedBy(customer);
        assertEquals("The leasedBy field should return the Customer instance now associated with this instance.",
                customer, car.getLeasedBy());

        car.setLeasedBy(null);
        assertEquals("The leasedBy field should be reset to null when nulling the setLeasedBy parameter..",
                null, car.getLeasedBy());
    }

    @Test
    public void testIsSetLeased() throws Exception {
        assertEquals("The leased field should return false prior to initial lease.",
                false, car.isLeased());

        car.setLeased(true);
        assertEquals("The leasedTimes field should return true when set to true.",
                true, car.isLeased());

        car.setLeased(false);
        assertEquals("The leasedTimes field should return to being false ending lease.",
                false, car.isLeased());
    }

    @Test
    public void testEquals() throws Exception {
        final LeaseCar leaseCar = CarFactory.newCar();
        assertNotEquals("The previously instantiated LeaseCar instance should not be equal to a newly generated one.",
                car, leaseCar);

        final LeaseCar equalLeaseCar = new LeaseCar(car.getRegistrationNumber());
        assertEquals("The previously instantiated LeaseCar instance should be considered " +
                        "equal to the manually instantiated one due to their nature as value objects.",
                car, equalLeaseCar);

        assertEquals("The manually instantiated LeaseCars should be equal",
                new LeaseCar("TEST"), new LeaseCar("TEST"));
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals("The hashcode of the previously instantiated LeaseCar should " +
                        "match the hashcode of it's registrationNumber field.",
                car.hashCode(), car.getRegistrationNumber().hashCode());
    }
}