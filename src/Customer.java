/**
 * Customer thread.
 *
 * Life cycle (Customer threads repeat this process):
 * - Wait between 1 to 10 seconds
 * - Contact producer for resource
 * - Wait between 1 to 3 seconds
 * - Release resource
 *
 */
public class Customer implements Runnable {
    private String customerName;
    private Leaser leaser;

    public Customer(final String customerName, Leaser leaser) {
        this.customerName = customerName;
        this.leaser = leaser;
    }

    public String getCustomerName() {
        return customerName;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000 + ((int) (Math.random() * 9000)));
                leaser.lease(this);
                Thread.sleep(1000 + ((int) (Math.random() * 2000)));
                leaser.endLease(this);
            } catch (InterruptedException e) {
                System.err.println("Thread " + customerName + " interrupted!");
            }


        }
    }
}
