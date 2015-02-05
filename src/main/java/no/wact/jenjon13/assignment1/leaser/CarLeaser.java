package no.wact.jenjon13.assignment1.leaser;

import no.wact.jenjon13.assignment1.car.CarFactory;
import no.wact.jenjon13.assignment1.car.LeaseCar;
import no.wact.jenjon13.assignment1.customer.Customer;

import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Singleton class responsible for leasing out LeaseCar objects to CarCustomer objects.
 */
public class CarLeaser extends Observable {
    private static final int LEASECAR_AMOUNT = 5;
    private static CarLeaser instance;
    private final ReentrantLock carsLock = new ReentrantLock(true);
    private final Condition lockCondition = carsLock.newCondition();
    private List<LeaseCar> cars;

    /**
     * Nonparametric private constructor for the class.
     */
    private CarLeaser() {
        cars = CarFactory.newCars(LEASECAR_AMOUNT);
    }

    /**
     * Getter for the instance of this singleton class.
     * @return Returns the singleton instance. Instantiates it if not already instantiated.
     */
    public static CarLeaser getInstance() {
        if (instance == null) {
            instance = new CarLeaser();
        }

        return instance;
    }

    /**
     * Getter for the class' cars list.
     * @return Returns the List of LeaseCar instances.
     */
    public List<LeaseCar> getCars() {
        return cars;
    }

    /**
     * Binds any available LeaseCar instance to the given Customer instance.
     * If no available LeaseCar instances are found, block and await one through lock Condition instance.
     * Notifies listening Observer instances of success when binding is initiated.
     * Thredsafe by utilizing a ReentrantLock instance.
     * @param customer The Customer instance to which the LeaseCar instance is to be bound.
     * @see no.wact.jenjon13.assignment1.leaser.CarLeaser#endLease
     */
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

    /**
     * Reverses the process of the lease method.
     * @see no.wact.jenjon13.assignment1.leaser.CarLeaser#lease
     */
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