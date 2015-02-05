package no.wact.jenjon13.assignment1.customer;

import no.wact.jenjon13.assignment1.leaser.CarLeaser;

/**
 * Customer thread.
 * <p>
 * Life cycle (Customer threads repeat this process):
 * - Wait between 1 to 10 seconds
 * - Contact producer for resource
 * - Wait between 1 to 3 seconds
 * - Release resource
 */
public final class CarCustomer extends Customer {
    public CarCustomer(final String customerName, CarLeaser leaser) {
        super(customerName, leaser);
    }


    public String getCustomerName() {
        return customerName;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Thread.sleep(1000 + ((int) (Math.random() * 2000)));
                leaser.lease(this);
                Thread.sleep(1000 + ((int) (Math.random() * 9000)));
                leaser.endLease(this);
            } catch (InterruptedException e) {
                System.err.println("Thread " + customerName + " interrupted!");
            }
        }
    }
}
