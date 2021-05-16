package app.domain.leaseService;

import app.utils.JsonProperty;
import app.utils.Utils;

import java.util.List;

public class Payment {
    private String customer;

    private Long leaseId;

    private Long paymentId;

    private int month;

    private int year;

    private float cost;

    private PaymentStatus paymentStatus;

    public String getCustomer() {
        return customer;
    }

    public Long getLeaseId() {
        return leaseId;
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