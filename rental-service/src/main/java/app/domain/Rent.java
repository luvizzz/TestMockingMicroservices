package app.domain;

import app.utils.CustomDateTimeDeserializer;
import app.utils.JsonProperty;
import app.utils.Utils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "rent")
@IdClass(RentId.class)
public class Rent {
    @Id
    private String customer;

    @Id
    private Long rentalId;

    private String carId;

    private PaymentStatus paymentStatus;

    private RentalStatus rentalStatus;

    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @ApiModelProperty(
            value = "Start Date of the rental",
            name = "startDate",
            dataType = "String",
            example = "2020-01-31")
    private DateTime startDate;

    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @ApiModelProperty(
            value = "End Date of the rental",
            name = "endDate",
            dataType = "String",
            example = "2020-01-31")
    private DateTime endDate;

    @ApiModelProperty(
            value = "Cost of the rental",
            name = "cost",
            example = "10.01")
    private float cost;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Long getRentalId() {
        return rentalId;
    }

    public String getCarId() {
        return carId;
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

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public RentalStatus getRentalStatus() {
        return rentalStatus;
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
