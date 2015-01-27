package no.wact.jenjon13.assignment1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
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
    }

    private static void initCustomers() {
        List<String> customerNames = getCustomerNames();
        final ExecutorService threadPool = Executors.newFixedThreadPool(customerNames.size());

        for (int i = 0; i < customerNames.size(); i++) {
            threadPool.execute(new Customer(customerNames.get(i), leaser));
        }

        threadPool.shutdown();
    }

    private static List<String> getCustomerNames() {
        List<String> customerNames = new ArrayList<>();
        try (final Scanner scanner = new Scanner(System.in)) {
            System.out.println("('-q' = done, '-a' = auto add 10 names");
            do {
                System.out.print("Customer name: ");
                customerNames.add(scanner.nextLine());
            } while (!(customerNames.get(customerNames.size() - 1).charAt(0) == '-'));

            String arg = customerNames.remove(customerNames.size() - 1);
            if (arg.charAt(1) == 'a') {
                customerNames.addAll(Arrays.asList(new String[]{"Per", "Lars", "Bente", "Erik", "Marit", "Kim", "Trude", "Hans", "Inger", "Jonas"}));
            }
        }

        return customerNames;
    }
}

// TODO: UNIT TESTS!
// TODO: DOCUMENTATION - METHOD HEADERS
// TODO: LOOK AT ConsuperProducer.java
// TODO: GUI INTERFACE YUUUSSS
// TODO: weignting, cost, ect