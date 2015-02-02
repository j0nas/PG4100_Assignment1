package no.wact.jenjon13.assignment1;

import no.wact.jenjon13.assignment1.car.Car;
import no.wact.jenjon13.assignment1.customer.Customer;
import no.wact.jenjon13.assignment1.customer.CustomerFactory;
import no.wact.jenjon13.assignment1.gui.View;
import no.wact.jenjon13.assignment1.leaser.CarLeaser;
import no.wact.jenjon13.assignment1.leaser.LeaserFactory;
import no.wact.jenjon13.assignment1.util.ExternalResources;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller implements Observer {
    private final int CUSTOMER_MIN_AMOUNT = 5;

    private final List<String> customerNames;
    private final List<Car> cars;

    private final ExecutorService threadPool;
    private CarLeaser leaser;
    private View view;
    private boolean startedRunning;

    public Controller() {
        cars = new ArrayList<>();
        leaser = LeaserFactory.newLeaser(cars);
        view = new View("Client GUI", cars);
        leaser.addObserver(view);

        threadPool = Executors.newCachedThreadPool();
        customerNames = new ArrayList<>();

        final ExternalResources resources = new ExternalResources();
        resources.addObserver(this);
        resources.notifyCustomerNames();
        threadPool.shutdown();
    }

    public static void main(String[] args) {
        new Controller();
    }

    @Override
    public void update(final Observable o, final Object arg) {
        customerNames.add(arg.toString());

        if (!startedRunning && (customerNames.size() < CUSTOMER_MIN_AMOUNT)) {
            System.out.println("Add " + (CUSTOMER_MIN_AMOUNT - customerNames.size()) + " more names to start.");
        } else {
            while (!customerNames.isEmpty()) {
                System.out.println(customerNames.size());

                final String remove = customerNames.remove(customerNames.size() - 1);
                System.out.println(remove);

                final Customer newCustomer = CustomerFactory.newCustomer(remove, leaser);
                System.out.println(newCustomer.getCustomerName());
                threadPool.execute(newCustomer);
            }

            startedRunning = true;
        }
    }
}

// TODO: UNIT TESTS!
// TODO: DOCUMENTATION - METHOD HEADERS
// TODO: LOOK AT ConsuperProducer.java
// TODO: GUI INTERFACE YUUUSSS
// TODO: weignting, cost, ect