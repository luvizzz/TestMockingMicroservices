package app.domain;

import app.utils.CustomDateTimeDeserializer;
import app.utils.JsonProperty;
import app.utils.Utils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import org.apache.logging.log4j.util.Strings;
import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "lease")
@IdClass(LeaseId.class)
public class Lease {
    @Id
    private String customer;

    @Id
    private Long leaseId;

    private String carId;

    @OneToMany
    private List<Payment> payments;

    private LeaseStatus leaseStatus;

    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @ApiModelProperty(
            value = "Start Date of the lease",
            name = "startDate",
            dataType = "String",
            example = "2020-01-31")
    private DateTime startDate;

    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @ApiModelProperty(
            value = "End Date of the lease",
            name = "endDate",
            dataType = "String",
            example = "2020-01-31")
    private DateTime endDate;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setLeaseStatus(LeaseStatus leaseStatus) {
        this.leaseStatus = leaseStatus;
    }

    public Long getLeaseId() {
        return leaseId;
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

    public List<Payment> getPayments() {
        return payments;
    }

    public LeaseStatus getLeaseStatus() {
        return leaseStatus;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public String toJsonString() {
        return Utils.toJson(List.of(
                new JsonProperty("customer", customer == null ? "null" : customer),
                new JsonProperty("leaseId", leaseId == null ? "null" : leaseId),
                new JsonProperty("carId", carId == null ? "null" : carId),
                new JsonProperty("startDate", startDate == null ? "null" : startDate.toString()),
                new JsonProperty("endDate", endDate == null ? "null" : endDate.toString()),
                new JsonProperty("LeaseStatus", leaseStatus == null ? "null" : leaseStatus.toString()),
                new JsonProperty("payments", paymentsToString())
            ));
    }

    private String paymentsToString() {
        if (payments == null) {
            return Strings.EMPTY;
        }
        return payments.stream()
                .map(Object::toString)
                .collect(Collectors.joining("; " , "[", "]"));
    }
}
