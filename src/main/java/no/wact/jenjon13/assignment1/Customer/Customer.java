package no.wact.jenjon13.assignment1.customer;

import no.wact.jenjon13.assignment1.leaser.CarLeaser;

/**
 * Abstract value object representing a customer thread.
 */
public abstract class Customer implements Runnable, Comparable {
    protected final String customerName;
    protected final CarLeaser leaser;

    public Customer(final String customerName, final CarLeaser leaser) {
        this.customerName = customerName;
        this.leaser = leaser;
    }

    public String getCustomerName() {
        return customerName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Customer customer = (Customer) o;
        return !(customerName != null ? !customerName.equals(customer.customerName) : customer.customerName != null);
    }

    @Override
    public int hashCode() {
        return customerName != null ? customerName.hashCode() : 0;
    }

    @Override
    public int compareTo(final Object o) {
        return (o instanceof Customer) ? -1 : ((Customer) o).equals(o) ? 0 : 1;
    }
}
