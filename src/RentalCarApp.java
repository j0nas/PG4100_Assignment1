import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RentalCarApp {
    public static List<LeaseCar> cars = new ArrayList<>();

    // TODO: get customers from scanner
    private static int CUSTOMER_COUNT = 10;
    private static int CUSTOMER_MAX_AMOUNT = 10;
    private static int CUSTOMER_MIX_AMOUNT = 5;
    private static Leaser leaser;

    public static void main(String[] args) {
        initLeaser(cars);
        initCustomers();
    }

    private static void initLeaser(final List<LeaseCar> cars) {
        leaser = new Leaser(cars);
        // TODO: Is it right to let Leaser be responsible for their own car instantiation?
    }

    private static void initCustomers() {
        String[] customerNames = {"Per", "Lars", "Bente", "Erik", "Marit", "Kim", "Trude", "Hans", "Inger", "Jonas"};
        final ExecutorService threadPool = Executors.newFixedThreadPool(customerNames.length);

        for (int i = 0; i < customerNames.length; i++) {
            threadPool.execute(new Customer(customerNames[i], leaser));
        }

        threadPool.shutdown();
    }
}

// TODO: UNIT TESTS!
// TODO: DOCUMENTATION - METHOD HEADERS
// TODO: LOOK AT ConsuperProducer.java
