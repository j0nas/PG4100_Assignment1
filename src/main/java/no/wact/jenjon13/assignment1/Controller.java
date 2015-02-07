package no.wact.jenjon13.assignment1;

import no.wact.jenjon13.assignment1.customer.Customer;
import no.wact.jenjon13.assignment1.customer.CustomerFactory;
import no.wact.jenjon13.assignment1.gui.View;
import no.wact.jenjon13.assignment1.leaser.CarLeaser;

import java.util.Observable;
import java.util.Observer;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Controller of the application.
 * Responsible for managing the View and Model instances.
 */
public final class Controller implements Observer {
    private final int CUSTOMER_MIN_AMOUNT = 5;
    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    private final View view = new View("Client GUI", CarLeaser.getInstance().getCars());
    private final Queue<Customer> customers = new PriorityQueue<>();
    private boolean startedRunning;

    /**
     * Constructor for the class.
     */
    public Controller() {
        view.addObserver(this);
        CarLeaser.getInstance().addObserver(view);
    }

    /**
     * Main method for the application. Execution flow starts here.
     *
     * @param args Not used.
     */
    public static void main(String[] args) {
        new Controller();
    }

    /**
     * Gets called when the instance that this class observes notifies observers of updates.
     * This controller observes updates that occur in the View.
     * Provides a more loosely coupled attachement to the View.
     * Recieves user input from the View, which are interpreted as CarCustomer names.
     * CarCustomer instances are created based on this observed user input.
     *
     * @param o   The Observable that dispatched the update call.
     * @param arg The argument that the Observable object passed in the update dispatcher method.
     */
    @Override
    public void update(final Observable o, final Object arg) {
        final Customer customer = CustomerFactory.newCustomer(arg.toString());
        if (customer == null) {
            System.out.println("Maximum amount of customers added. " +
                    "Shutting down threadpool to restrict further access.");
            threadPool.shutdown();
            view.deleteObserver(this);
            return;
        }

        System.out.println(customer.getName() + " added: " + customers.add(customer));
        if (!startedRunning && (customers.size() < CUSTOMER_MIN_AMOUNT)) {
            System.out.println("Add " + (CUSTOMER_MIN_AMOUNT - customers.size()) + " more names to start.");
        } else {
            if (!startedRunning) {
                System.out.println("Started!");
                startedRunning = true;
            }

            while (!customers.isEmpty()) {
                threadPool.execute(customers.poll());
            }
        }
    }
}