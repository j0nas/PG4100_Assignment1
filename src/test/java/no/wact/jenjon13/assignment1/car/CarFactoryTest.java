package no.wact.jenjon13.assignment1.car;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class CarFactoryTest {
    @Test
    public void testNewCar() throws Exception {
        final LeaseCar leaseCar = CarFactory.newCar();
        assertEquals("The created leaseCar instance should have the expected license plate value",
                "RF66666", leaseCar.getRegistrationNumber());
    }

    @Test
    public void testNewCars() throws Exception {
        final List<LeaseCar> leaseCars = CarFactory.newCars(5);
        for (int i = 0; i < leaseCars.size(); i++) {
            String expectedRegNum = "RF";
            for (int j = 0; j < 5; j++) {
                expectedRegNum += (i + 1);
            }

            assertEquals(String.format("Car #%d should have the expected licence plate value of %s", i, expectedRegNum),
                    expectedRegNum, leaseCars.get(i).getRegistrationNumber());
        }
    }
}
