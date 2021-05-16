package app.domain;

import app.domain.leaseService.Lease;
import app.domain.rentalService.Rent;
import org.springframework.lang.Nullable;

public class CustomerMostRecent {

    @Nullable
    private Rent rental;

    @Nullable
    private Lease lease;

    public Rent getRental() {
        return rental;
    }

    public Lease getLease() {
        return lease;
    }

    public CustomerMostRecent(Rent rental, Lease lease) {
        if (rental != null) this.rental = rental;
        if (lease != null) this.lease = lease;
    }
}
