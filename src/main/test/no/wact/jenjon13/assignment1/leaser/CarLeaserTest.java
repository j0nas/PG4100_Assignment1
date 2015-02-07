package no.wact.jenjon13.assignment1.leaser;

import no.wact.jenjon13.assignment1.car.LeaseCar;
import no.wact.jenjon13.assignment1.customer.Customer;
import no.wact.jenjon13.assignment1.customer.CustomerFactory;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class CarLeaserTest {
    @Test
    public void testGetInstance() throws Exception {
        assertEquals("The returned instance should match the expected instance.",
                CarLeaser.class, CarLeaser.getInstance().getClass());

        assertTrue("The returned instance should have the expected origin.",
                CarLeaser.getInstance() instanceof CarLeaser);
    }

    @Test
    public void testGetCars() throws Exception {
        final LeaseCar testCar = new LeaseCar("TEST");
        CarLeaser.getInstance().getCars().forEach(car ->
                assertTrue("All cars held by the CarLeaser instance should be valid and unique.",
                        car instanceof LeaseCar && !car.equals(testCar)));
    }

    @Test
    public void testLeaseAndEndLease() throws Exception {
        final Customer test = CustomerFactory.newCustomer("TEST");
        CarLeaser.getInstance().lease(test);

        final Optional<LeaseCar> carOptional = CarLeaser.getInstance()
                .getCars()
                .parallelStream()
                .filter(car -> car.getLeasedBy() != null && car.getLeasedBy().equals(test))
                .findAny();
        assertTrue("A car should now be set to 'leasedBy' by the test customer instance.",
                carOptional.isPresent());

        CarLeaser.getInstance().endLease(test);
        assertFalse("The previously leased car should now not be leased.",
                carOptional.get().isLeased());
    }
}