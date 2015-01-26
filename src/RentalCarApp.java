import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RentalCarApp {
    private static int CUSTOMER_COUNT;
    private static int CUSTOMER_MAX_AMOUNT = 10;
    private static int CUSTOMER_MIX_AMOUNT = 5;

    private static Leaser leaser;

    public static void main(String[] args) {
        initCustomers();
        initLeaser();
    }

    private static void initLeaser() {
        leaser = new Leaser();
        // TODO: Is it right to let Leaser be responsible for their own car instantiation?
    }

    private static void initCustomers() {
        final ExecutorService threadPool = Executors.newFixedThreadPool(10);
        for (int i = 0; i > CUSTOMER_COUNT; i++) {
            threadPool.execute(new Customer(String.valueOf(Math.random() * 1000)));
            // TODO: Get customers name from Scanner
        }

        threadPool.shutdown();
    }
}
