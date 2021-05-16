package app.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class PaymentId implements Serializable {
    @Id
    private String customer;

    @Id
    private Long leaseId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentId;

    public PaymentId(String customer, Long leaseId, Long paymentId) {
        this.customer = customer;
        this.leaseId = leaseId;
        this.paymentId = paymentId;
    }

    public PaymentId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentId paymentId1 = (PaymentId) o;

        if (!Objects.equals(customer, paymentId1.customer)) return false;
        if (!Objects.equals(leaseId, paymentId1.leaseId)) return false;
        return Objects.equals(paymentId, paymentId1.paymentId);
    }

    @Override
    public int hashCode() {
        int result = customer != null ? customer.hashCode() : 0;
        result = 31 * result + (leaseId != null ? leaseId.hashCode() : 0);
        result = 31 * result + (paymentId != null ? paymentId.hashCode() : 0);
        return result;
    }
}
