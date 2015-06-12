package no.wact.jenjon13.assignment1.customer;

import no.wact.jenjon13.assignment1.car.LeaseCar;
import no.wact.jenjon13.assignment1.leaser.CarLeaser;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void testLifeCycle() throws Exception {
        final CarCustomer testCustomer = new CarCustomer("TEST");

        CarLeaser carLeaser = CarLeaser.getInstance();
        carLeaser = null;
        carLeaser = CarLeaser.getInstance();

        final Thread thread = new Thread(testCustomer);
        thread.start();
        Thread.sleep(3500);
        thread.stop();


        final Optional<LeaseCar> carIfPresent = carLeaser.getCars()
                .parallelStream()
                .filter(car -> car.getLeasedTimes() > 0)
                .findAny();

        assertTrue("During the thread's lifecycle, it should lease and terminate lease of at least one car,",
                carIfPresent.isPresent());
    }
}
