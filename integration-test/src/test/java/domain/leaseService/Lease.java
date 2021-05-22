package domain.leaseService;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL) 	//  ignore all null fields
public class Lease {
    @Nullable
    private String customer;

    @Nullable
    private Integer leaseId;

    @Nullable
    private String carId;

    @Nullable
    private List<Payment> payments;

    @Nullable
    private LeaseStatus leaseStatus;

    @Nullable
    private String startDate;

    @Nullable
    private String endDate;

    public static class Builder {
        private String customer;
        private Integer leaseId;
        private String carId;
        private List<Payment> payments;
        private LeaseStatus leaseStatus;
        private String startDate;
        private String endDate;

        public Builder() { }

        public Builder withCustomer(String customer) {
            this.customer = customer;
            return this;
        }

        public Builder withLeaseId(Integer leaseId) {
            this.leaseId = leaseId;
            return this;
        }

        public Builder withCarId(String carId) {
            this.carId = carId;
            return this;
        }

        public Builder withPayments(List<Payment> payments) {
            this.payments = payments;
            return this;
        }

        public Builder withLeaseStatus(LeaseStatus leaseStatus) {
            this.leaseStatus = leaseStatus;
            return this;
        }

        public Builder withStartDate(String startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder withEndDate(String endDate) {
            this.endDate = endDate;
            return this;
        }

        public Lease build() {
            Lease lease = new Lease();
            Optional.ofNullable(this.customer).ifPresent(value -> lease.customer = value);
            Optional.ofNullable(this.leaseId).ifPresent(value -> lease.leaseId = value);
            Optional.ofNullable(this.carId).ifPresent(value -> lease.carId = value);
            Optional.ofNullable(this.payments).ifPresent(value -> lease.payments = value);
            Optional.ofNullable(this.leaseStatus).ifPresent(value -> lease.leaseStatus = value);
            Optional.ofNullable(this.startDate).ifPresent(value -> lease.startDate = value);
            Optional.ofNullable(this.endDate).ifPresent(value -> lease.endDate = value);
            return lease;
        }
    }

    @Nullable
    public String getCustomer() {
        return customer;
    }

    public void setCustomer(@Nullable String customer) {
        this.customer = customer;
    }

    @Nullable
    public Integer getLeaseId() {
        return leaseId;
    }

    public void setLeaseId(@Nullable Integer leaseId) {
        this.leaseId = leaseId;
    }

    @Nullable
    public String getCarId() {
        return carId;
    }

    public void setCarId(@Nullable String carId) {
        this.carId = carId;
    }

    @Nullable
    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(@Nullable List<Payment> payments) {
        this.payments = payments;
    }

    @Nullable
    public LeaseStatus getLeaseStatus() {
        return leaseStatus;
    }

    public void setLeaseStatus(@Nullable LeaseStatus leaseStatus) {
        this.leaseStatus = leaseStatus;
    }

    @Nullable
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(@Nullable String startDate) {
        this.startDate = startDate;
    }

    @Nullable
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(@Nullable String endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lease)) return false;

        Lease lease = (Lease) o;

        if (customer != null ? !customer.equals(lease.customer) : lease.customer != null) return false;
        if (leaseId != null ? !leaseId.equals(lease.leaseId) : lease.leaseId != null) return false;
        if (carId != null ? !carId.equals(lease.carId) : lease.carId != null) return false;
        if (payments != null ? !payments.equals(lease.payments) : lease.payments != null) return false;
        if (leaseStatus != lease.leaseStatus) return false;
        if (startDate != null ? !startDate.equals(lease.startDate) : lease.startDate != null) return false;
        return endDate != null ? endDate.equals(lease.endDate) : lease.endDate == null;
    }

    @Override
    public int hashCode() {
        int result = customer != null ? customer.hashCode() : 0;
        result = 31 * result + (leaseId != null ? leaseId.hashCode() : 0);
        return result;
    }
}
