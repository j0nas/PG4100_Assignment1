package no.wact.jenjon13.assignment1;

import no.wact.jenjon13.assignment1.customer.Customer;
import no.wact.jenjon13.assignment1.customer.CustomerFactory;
import no.wact.jenjon13.assignment1.gui.View;
import no.wact.jenjon13.assignment1.leaser.CarLeaser;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller implements Observer {
    private final int CUSTOMER_MIN_AMOUNT = 5;
    private final int CUSTOMER_MAX_AMOUNT = 10;
    private final List<String> customerNames;
    private final ExecutorService threadPool;
    private int customerCount = 0;
    private CarLeaser leaser;
    private View view;
    private boolean startedRunning;

    public Controller() {
        leaser = CarLeaser.getInstance();
        view = new View("Client GUI", leaser.getCars());
        leaser.addObserver(view);

        threadPool = Executors.newCachedThreadPool();
        customerNames = new ArrayList<>();

//        final ExternalResources resources = new ExternalResources();
//        resources.addObserver(this);
//        resources.notifyCustomerNames();
        view.addObserver(this);
    }

    public static void main(String[] args) {
        new Controller();
    }

    @Override
    public void update(final Observable o, final Object arg) {
        if (customerCount < CUSTOMER_MAX_AMOUNT) {
            customerNames.add(arg.toString());
            System.out.println(arg + " added.");
        }

        if (!startedRunning && (customerNames.size() < CUSTOMER_MIN_AMOUNT)) {
            System.out.println("Add " + (CUSTOMER_MIN_AMOUNT - customerNames.size()) + " more names to start.");
        } else {
            if (!startedRunning) {
                System.out.println("Started!");
                startedRunning = true;
            }

            while (!customerNames.isEmpty()) {
                final int index = customerNames.size() - 1;
                final String remove = customerNames.remove(index);
                final Customer command = CustomerFactory.newCustomer(remove, leaser);
                threadPool.execute(command);
                customerCount++;

                if (customerCount >= CUSTOMER_MAX_AMOUNT) {
                    System.out.println("Maximum amount of customers added. " +
                            "Shutting down threadpool to restrict further access.");
                    threadPool.shutdown();
                }
            }

        }
    }
}

// TODO: UNIT TESTS!
// TODO: DOCUMENTATION - METHOD HEADERS
// TODO: LOOK AT ConsuperProducer.java
// TODO: GUI INTERFACE YUUUSSS
// TODO: weignting, cost, ect