import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Leaser {
    private final String LICENSEPLATE_PREFIX = "RF";
    private final int LEASECAR_AMOUNT = 5;
    private final int LICENSEPLATE_LENGTH = 5;
    private final int LICENSEPLATE_INIT_NUM = 1;
    private final ReentrantLock carsLock = new ReentrantLock(true);
    private final Condition lockCondition = carsLock.newCondition();
    private List<LeaseCar> cars;

    public Leaser(final List<LeaseCar> cars) {
        StringBuilder license = new StringBuilder(LICENSEPLATE_LENGTH);
        for (int i = LICENSEPLATE_INIT_NUM; i < LICENSEPLATE_INIT_NUM + LEASECAR_AMOUNT; i++) {
            license.append(LICENSEPLATE_PREFIX);
            for (int j = 0; j < LICENSEPLATE_LENGTH; j++) {
                license.append(String.valueOf(i));
            }

            cars.add(new LeaseCar(license.toString()));
            license.delete(0, LICENSEPLATE_PREFIX.length() + LICENSEPLATE_LENGTH);
        }

        this.cars = cars;
    }

    public void lease(final Customer customer) {
        carsLock.lock();
        try {
            final Optional<LeaseCar> availableCar = cars.parallelStream().filter(car -> !car.isLeased()).findFirst();

            try {
                if (!availableCar.isPresent()) {
                    lockCondition.await();
                    lease(customer);
                    return;
                }

                System.out.println(availableCar.get().getRegistrationNumber() + " locked by " + customer.getCustomerName());
                availableCar.get().setLeased(true);
                availableCar.get().setLeasedBy(customer.getCustomerName());
            } catch (InterruptedException e) {
                System.err.println("Leaser.lease() :: " + e.getMessage());
            }
        } finally {
            carsLock.unlock();
        }
    }

    public void endLease(final Customer customer) {
        carsLock.lock();
        try {
            final Optional<LeaseCar> leasedCar = cars.parallelStream()
                    .filter(car -> car.isLeased() && car.getLeasedBy().equals(customer.getCustomerName())).findFirst();

            if (leasedCar.isPresent()) {
                System.out.println(leasedCar.get().getRegistrationNumber() + " unlocked by " + leasedCar.get().getLeasedBy());
                leasedCar.get().setLeased(false);
                leasedCar.get().setLeasedBy("");

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