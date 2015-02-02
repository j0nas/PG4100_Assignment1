package no.wact.jenjon13.assignment1.leaser;

import no.wact.jenjon13.assignment1.car.Car;
import no.wact.jenjon13.assignment1.car.CarFactory;
import no.wact.jenjon13.assignment1.customer.Customer;

import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CarLeaser extends Observable {
    private final String LICENSEPLATE_PREFIX = "RF";
    private final int LEASECAR_AMOUNT = 5;
    private final int LICENSEPLATE_LENGTH = 5;
    private final int LICENSEPLATE_INIT_NUM = 1;
    private final ReentrantLock carsLock = new ReentrantLock(true);
    private final Condition lockCondition = carsLock.newCondition();
    private List<Car> cars;

    public CarLeaser(final List<Car> cars) {
        final StringBuilder license = new StringBuilder(LICENSEPLATE_LENGTH);
        for (int i = LICENSEPLATE_INIT_NUM; i < LICENSEPLATE_INIT_NUM + LEASECAR_AMOUNT; i++) {
            license.append(LICENSEPLATE_PREFIX);
            for (int j = 0; j < LICENSEPLATE_LENGTH; j++) {
                license.append(String.valueOf(i));
            }

            cars.add(CarFactory.newCar((license.toString())));
            license.delete(0, LICENSEPLATE_PREFIX.length() + LICENSEPLATE_LENGTH);
        }

        this.cars = cars;
    }

    public void lease(final Customer customer) {
        carsLock.lock();
        try {
            final Optional<Car> availableCar = cars
                    .parallelStream()
                    .filter(car -> !car.isLeased())
                    .findFirst();

            if (!availableCar.isPresent()) {
                try {
                    lockCondition.await();
                } catch (InterruptedException e) {
                    System.err.println("Leaser.lease() :: " + e.getMessage());
                }

                lease(customer);
                return;
            }

            final Car car = availableCar.get();
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
            final Optional<Car> leasedCar = cars.parallelStream()
                    .filter(car -> car.isLeased() && car.getLeasedBy().equals(customer.getCustomerName()))
                    .findFirst();

            if (leasedCar.isPresent()) {
                final Car car = leasedCar.get();
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