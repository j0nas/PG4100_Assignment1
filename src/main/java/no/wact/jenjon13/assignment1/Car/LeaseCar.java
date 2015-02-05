package no.wact.jenjon13.assignment1.car;

/**
 * Value object representing a resource held by CarLeaser.
 * Acquired by CarCustomer instances through CarLeaser.
 */
public class LeaseCar {
    private String registrationNumber;
    private boolean leased;
    private String leasedBy;
    private int leasedTimes = 0;

    /**
     * Constructor for the class.
     * @param registrationNumber The identifier of the car, a String representing a license plate value.
     */
    public LeaseCar(final String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    /*
     * Getters and setters for the respective fields.
     */
    public int getLeasedTimes() {
        return leasedTimes;
    }

    public String getLeasedBy() {
        return leasedBy;
    }

    public void setLeasedBy(final String leasedBy) {
        this.leasedBy = leasedBy;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public boolean isLeased() {
        return leased;
    }

    public void setLeased(final boolean leased) {
        if (!this.leased && leased) {
            this.leasedTimes++;
        }

        this.leased = leased;
    }

    /**
     * @see no.wact.jenjon13.assignment1.customer.Customer#equals
     */
    @Override
    public boolean equals(final Object o) {
        return (this == o) || (!(o == null || getClass() != o.getClass()) &&
                (registrationNumber.equals(((LeaseCar) o).registrationNumber)));
    }

    /**
     * @see no.wact.jenjon13.assignment1.customer.Customer#hashCode
     */
    @Override
    public int hashCode() {
        return registrationNumber.hashCode();
    }
}
