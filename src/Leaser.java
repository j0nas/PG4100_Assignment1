import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Leaser {
    public static final String LICENSEPLATE_PREFIX = "RF";
    private final int LEASECAR_AMOUNT = 5;
    private final int LICENSEPLATE_LENGTH = 5;
    private final int LICENSEPLATE_INIT_NUM = 1;
    private Map<LeaseCar, Lock> carMap = new HashMap<>(LEASECAR_AMOUNT);

    public Leaser(final List<LeaseCar> cars) {
        InitCars(cars);
        cars.forEach(car -> carMap.put(car, new ReentrantLock(true)));
    }

    private void InitCars(final List<LeaseCar> cars) {
        StringBuilder license = new StringBuilder(LICENSEPLATE_LENGTH);
        for (int i = LICENSEPLATE_INIT_NUM; i < LICENSEPLATE_INIT_NUM + LEASECAR_AMOUNT; i++) {
            license.append(LICENSEPLATE_PREFIX);
            for (int j = 0; j < LICENSEPLATE_LENGTH; j++) {
                license.append(String.valueOf(i));
            }

            cars.add(new LeaseCar(license.toString()));
            license.delete(0, LICENSEPLATE_PREFIX.length() + LICENSEPLATE_LENGTH);
        }
    }

    public synchronized void lease(final Customer customer) {
        final Optional<LeaseCar> availableCar = carMap.keySet().parallelStream().filter(car -> !car.isLeased()).findFirst();
        System.out.println(availableCar.isPresent() ? availableCar.get().getRegistrationNumber() + " available" : "No unleased car");

        if (availableCar.isPresent()) {
            carMap.get(availableCar.get()).lock();
            System.out.println(availableCar.get().getRegistrationNumber() + " locked!");
            availableCar.get().setLeased(true);
            availableCar.get().setLeasedBy(customer.getCustomerName());
        }
    }

    public void endLease(final Customer customer) {
        final Optional<LeaseCar> leasedCar = carMap.keySet().parallelStream()
                .filter(car -> car.isLeased() && car.getLeasedBy().equals(customer.getCustomerName())).findFirst();

        if (leasedCar.isPresent()) {
            final LeaseCar leaseCar = leasedCar.get();
            leaseCar.setLeased(false);
            leaseCar.setLeasedBy("");
            carMap.get(leaseCar).unlock();
            System.out.println(leaseCar.getRegistrationNumber() + " unlocked!");
        }
    }
}
