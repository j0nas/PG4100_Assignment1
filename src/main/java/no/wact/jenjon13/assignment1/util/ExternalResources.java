package no.wact.jenjon13.assignment1.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;

public class ExternalResources extends Observable {
    private static int CUSTOMER_MAX_AMOUNT = 10;

    public void notifyCustomerNames() {
        List<String> customerNames = new ArrayList<>();

        try (final Scanner scanner = new Scanner(System.in)) {
            System.out.println("('-q' = done)");
            do {
                System.out.print("Customer name: ");
                customerNames.add(scanner.nextLine());

                if (customerNames.get(customerNames.size() - 1).equals("-q")) {
                    return;
                }

                setChanged();
                notifyObservers(customerNames.get(customerNames.size() - 1));
            } while (customerNames.size() < CUSTOMER_MAX_AMOUNT);
            System.out.println("Maximum amount of customers reached.");
        }
    }
}
