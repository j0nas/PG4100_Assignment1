import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class Leaser {
    public static final int CAR_COUNT = 5;

    private Map<ReentrantLock, LeaseCar> cars;

    public Leaser() {
        for (int i = 0; i < CAR_COUNT; i++) {
            cars.put(new ReentrantLock(true), new LeaseCar(String.valueOf(Math.random() * 1000)));
        }
    }

    public void lease() {
        final Optional<ReentrantLock> freeLock = cars.keySet().stream().filter(lock -> lock.isLocked()).findFirst();
        System.out.println(freeLock != null ? cars.get(freeLock).getRegistrationNumber() : "Lock is null!");
    }

    public void endLease() {

    }
}
