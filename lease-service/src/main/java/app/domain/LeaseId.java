package app.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class LeaseId implements Serializable {
    @Id
    private String customer;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long leaseId;

    public LeaseId(String customer, Long leaseId) {
        this.customer = customer;
        this.leaseId = leaseId;
    }

    public LeaseId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LeaseId leaseId = (LeaseId) o;

        if (!Objects.equals(customer, leaseId.customer))
            return false;
        return Objects.equals(this.leaseId, leaseId.leaseId);
    }

    @Override
    public int hashCode() {
        int result = customer != null ? customer.hashCode() : 0;
        result = 31 * result + (leaseId != null ? leaseId.hashCode() : 0);
        return result;
    }
}
