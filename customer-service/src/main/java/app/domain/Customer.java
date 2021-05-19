package app.domain;

import app.domain.leaseService.Lease;
import app.domain.rentalService.Rent;
import app.utils.JsonProperty;
import app.utils.Utils;
import org.apache.logging.log4j.util.Strings;

import java.util.List;
import java.util.stream.Collectors;

public class Customer {

    private final List<Rent> rentals;

    private final List<Lease> leases;

    public Customer(List<Rent> rentals, List<Lease> leases) {
        this.rentals = rentals;
        this.leases = leases;
    }

    public List<Rent> getRentals() {
        return rentals;
    }

    public List<Lease> getLeases() {
        return leases;
    }

    public String toJsonString() {
        return Utils.toJson(List.of(
                new JsonProperty("rentals", listToJsonString(rentals)),
                new JsonProperty("leases", listToJsonString(leases))
        ));
    }

    private String listToJsonString(List list) {
        if (list == null) {
            return Strings.EMPTY;
        }
        return (String) list.stream()
                .map(Object::toString)
                .collect(Collectors.joining("; " , "[", "]"));
    }
}
