package app.domain.rentalService;

import app.utils.JsonProperty;
import app.utils.Utils;
import org.joda.time.DateTime;

import java.util.List;

public class Rent {
    private String customer;

    private Long rentalId;

    private String carId;

    private PaymentStatus paymentStatus;

    private RentalStatus rentalStatus;

    private DateTime startDate;

    private DateTime endDate;

    private float cost;

    public Rent() {}

    public String getCustomer() {
        return customer;
    }

    public Long getRentalId() {
        return rentalId;
    }

    public String getCarId() {
        return carId;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public RentalStatus getRentalStatus() {
        return rentalStatus;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public float getCost() {
        return cost;
    }

    public String toJsonString() {
        return Utils.toJson(List.of(
                new JsonProperty("customer", customer == null ? "null" : customer),
                new JsonProperty("rentalId", rentalId == null ? "null" : rentalId),
                new JsonProperty("carId", carId == null ? "null" : carId),
                new JsonProperty("startDate", startDate == null ? "null" : startDate.toString()),
                new JsonProperty("endDate", endDate == null ? "null" : endDate.toString()),
                new JsonProperty("cost", cost),
                new JsonProperty("paymentStatus", paymentStatus == null ? "null" : paymentStatus.toString()),
                new JsonProperty("RentalStatus", rentalStatus == null ? "null" : rentalStatus.toString())
        ));
    }
}
