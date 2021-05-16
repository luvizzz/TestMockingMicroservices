package app.domain;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class RentId implements Serializable {
    @Id
    private String customer;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long rentalId;

    public RentId(String customer, Long rentalId) {
        this.customer = customer;
        this.rentalId = rentalId;
    }

    public RentId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RentId rentId = (RentId) o;

        if (!Objects.equals(customer, rentId.customer))
            return false;
        return Objects.equals(rentalId, rentId.rentalId);
    }

    @Override
    public int hashCode() {
        int result = customer != null ? customer.hashCode() : 0;
        result = 31 * result + (rentalId != null ? rentalId.hashCode() : 0);
        return result;
    }
}
