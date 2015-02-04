package no.wact.jenjon13.assignment1.util;

import java.util.*;

public class ExternalResources extends Observable {
    private static int CUSTOMER_MAX_AMOUNT = 10;

    public void notifyCustomerNames() {
        List<String> customerNames = new ArrayList<>();

        try (final Scanner scanner = new Scanner(System.in)) {
            System.out.println("('-a' = auto add 10 names, '-q' = done)");
            do {
                System.out.print("Customer name: ");
                customerNames.add(scanner.nextLine());

                switch (customerNames.get(customerNames.size() - 1)) {
                    case "-q":
                        return;
                    case "-a":
                        customerNames.remove(customerNames.size() - 1);
                        String[] names = {"Ane", "Adam", "Oda", "Trine", "Cecilie", "Irene", "Jonas", "Kim", "Oscar", "Martin"};
                        Arrays.asList(names).addAll(customerNames);
                        customerNames.forEach(name -> {
                            setChanged();
                            notifyObservers(name);
                            System.out.println(name + " added!");
                        });
                        return;
                }

                setChanged();
                notifyObservers(customerNames.get(customerNames.size() - 1));
            } while (customerNames.size() < CUSTOMER_MAX_AMOUNT);

            System.out.println("Maximum amount of customers reached.");
        }
    } // TODO: implement into view::observable
}
