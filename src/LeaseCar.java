public class LeaseCar {
    private String registrationNumber;
    private boolean leased;
    private String leasedBy;

    public LeaseCar(final String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getLeasedBy() {
        return leasedBy;
    }

    public void setLeasedBy(final String leasedBy) {
        this.leasedBy = leasedBy;
    }

    public boolean isLeased() {
        return leased;
    }

    public void setLeased(final boolean leased) {
        this.leased = leased;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        return (registrationNumber.equals(((LeaseCar) o).registrationNumber));
    }

    @Override
    public int hashCode() {
        return registrationNumber.hashCode();
    }

    @Override
    public String toString() {
        return String.format("LeaseCar{registrationNumber='%s', leased=%s, leasedBy='%s'}", registrationNumber, leased, leasedBy);
    }

    public String getRegistrationNumber() {
        return registrationNumber;

    }
}
