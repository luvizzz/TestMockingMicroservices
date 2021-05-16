package app.domain.leaseService;

import app.utils.JsonProperty;
import app.utils.Utils;
import org.apache.logging.log4j.util.Strings;
import org.joda.time.DateTime;

import java.util.List;
import java.util.stream.Collectors;

public class Lease {
    private String customer;

    private Long leaseId;

    private String carId;

    private List<Payment> payments;

    private LeaseStatus leaseStatus;

    private DateTime startDate;

    private DateTime endDate;

    public Lease() {}

    public String getCustomer() {
        return customer;
    }

    public Long getLeaseId() {
        return leaseId;
    }

    public String getCarId() {
        return carId;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public LeaseStatus getLeaseStatus() {
        return leaseStatus;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public DateTime getEndDate() {
        return endDate;
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
