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

    public Customer(final String customerName) {
        this.customerName = customerName;
    }

    @Override
    public void run() {

    }
}
