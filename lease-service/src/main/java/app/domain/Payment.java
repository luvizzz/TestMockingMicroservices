package app.domain;

import app.utils.JsonProperty;
import app.utils.Utils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "payment")
@IdClass(PaymentId.class)
public class Payment {
    @Id
    private String customer;

    @Id
    private Long leaseId;

    @Id
    private Long paymentId;

    private int month;

    private int year;

    private float cost;

    private PaymentStatus paymentStatus;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Long getLeaseId() {
        return leaseId;
    }

    public void setLeaseId(Long leaseId) {
        this.leaseId = leaseId;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public float getCost() {
        return cost;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String toJsonString() {
        return Utils.toJson(List.of(
                new JsonProperty("customer", customer == null ? "null" : customer),
                new JsonProperty("leaseId", leaseId == null ? "null" : leaseId),
                new JsonProperty("paymentId", paymentId == null ? "null" : paymentId),
                new JsonProperty("month", month),
                new JsonProperty("year", year),
                new JsonProperty("cost", cost),
                new JsonProperty("paymentStatus", paymentStatus == null ? "null" : paymentStatus.toString())
        ));
    }
}