package no.wact.jenjon13.assignment1.leaser;

import no.wact.jenjon13.assignment1.car.CarFactory;
import no.wact.jenjon13.assignment1.car.LeaseCar;
import no.wact.jenjon13.assignment1.customer.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Singleton class responsible for leasing out LeaseCar objects to CarCustomer objects.
 */
public class CarLeaser extends Observable {
    private static CarLeaser instance;
    private final String LICENSEPLATE_PREFIX = "RF";
    private final int LEASECAR_AMOUNT = 5;
    private final int LICENSEPLATE_LENGTH = 5;
    private final int LICENSEPLATE_INIT_NUM = 1;
    private final ReentrantLock carsLock = new ReentrantLock(true);
    private final Condition lockCondition = carsLock.newCondition();
    private List<LeaseCar> cars = new ArrayList<>();

    private CarLeaser() {
        final StringBuilder license = new StringBuilder(LICENSEPLATE_LENGTH);
        for (int i = LICENSEPLATE_INIT_NUM; i < LICENSEPLATE_INIT_NUM + LEASECAR_AMOUNT; i++) {
            license.append(LICENSEPLATE_PREFIX);
            for (int j = 0; j < LICENSEPLATE_LENGTH; j++) {
                license.append(String.valueOf(i));
            }

            cars.add(CarFactory.newCar((license.toString())));
            license.delete(0, LICENSEPLATE_PREFIX.length() + LICENSEPLATE_LENGTH);
        }
    }

    public static CarLeaser getInstance() {
        if (instance == null) {
            instance = new CarLeaser();
        }

        return instance;
    }

    public List<LeaseCar> getCars() {
        return cars;
    }

    public void lease(final Customer customer) {
        carsLock.lock();
        try {
            final Optional<LeaseCar> availableCar = cars
                    .parallelStream()
                    .filter(car -> !car.isLeased())
                    .findAny();

            if (!availableCar.isPresent()) {
                try {
                    lockCondition.await();
                } catch (InterruptedException e) {
                    System.err.println("Leaser.lease() :: " + e.getMessage());
                }

                lease(customer);
                return;
            }

            final LeaseCar car = availableCar.get();
            car.setLeased(true);
            car.setLeasedBy(customer.getCustomerName());
            setChanged();
            notifyObservers(car);
        } finally {
            carsLock.unlock();
        }
    }

    public void endLease(final Customer customer) {
        carsLock.lock();
        try {
            final Optional<LeaseCar> leasedCar = cars.parallelStream()
                    .filter(car -> car.isLeased() && car.getLeasedBy().equals(customer.getCustomerName()))
                    .findAny();

            if (leasedCar.isPresent()) {
                final LeaseCar car = leasedCar.get();
                car.setLeased(false);
                car.setLeasedBy("");
                setChanged();
                notifyObservers(car);

                try {
                    lockCondition.signal();
                } catch (IllegalMonitorStateException e) {
                    System.err.println("Leaser.endLease() :: " + e.getMessage());
                }
            }
        } finally {
            carsLock.unlock();
        }
    }
}