package no.wact.jenjon13.assignment1.customer;

import no.wact.jenjon13.assignment1.leaser.CarLeaser;

/**
 * Class representing a Customer Runnable.
 * Life cycle (Customer threads repeat this process):
 * - Wait between 1 to 10 seconds
 * - Contact producer for resource
 * - Wait between 1 to 3 seconds
 * - Release resource
 */
public final class CarCustomer extends Customer {

    /**
     * Constructor for the class.
     *
     * @param customerName The instance's identifier, as a String.
     */
    public CarCustomer(final String customerName) {
        super(customerName);
    }

    /**
     * Defining the lifecycle of the Runnable instance.
     */
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Thread.sleep(1000 + ((int) (Math.random() * 2000)));
                CarLeaser.getInstance().lease(this);
                Thread.sleep(1000 + ((int) (Math.random() * 9000)));
                CarLeaser.getInstance().endLease(this);
            } catch (InterruptedException e) {
                System.err.println("Thread " + customerName + " interrupted!");
            }
        }
    }
}
