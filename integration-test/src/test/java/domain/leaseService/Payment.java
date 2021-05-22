package domain.leaseService;

import domain.PaymentStatus;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Payment {
    private String customer;
    private Integer leaseId;
    private Integer paymentId;
    private Float cost;
    private PaymentStatus paymentStatus;
    private Integer month;
    private Integer year;

    public static class Builder {
        private String customer;
        private Integer leaseId;
        private Integer paymentId;
        private Float cost;
        private PaymentStatus paymentStatus;
        private Integer month;
        private Integer year;

        public Builder() { }

        public Builder withCustomer(String customer) {
            this.customer = customer;
            return this;
        }

        public Builder withLeaseId(Integer leaseId) {
            this.leaseId = leaseId;
            return this;
        }

        public Builder withPaymentId(Integer paymentId) {
            this.paymentId = paymentId;
            return this;
        }

        public Builder withCost(Float cost) {
            this.cost = cost;
            return this;
        }

        public Builder withPaymentStatus(PaymentStatus paymentStatus) {
            this.paymentStatus = paymentStatus;
            return this;
        }

        public Builder withMonth(Integer month) {
            this.month = month;
            return this;
        }

        public Builder withYear(Integer year) {
            this.year = year;
            return this;
        }

        public Payment build() {
            Payment rent = new Payment();
            rent.customer = this.customer;
            rent.leaseId = this.leaseId;
            rent.paymentId = this.paymentId;
            rent.cost = this.cost;
            rent.paymentStatus = this.paymentStatus;
            rent.month = this.month;
            rent.year = this.year;
            return rent;
        }
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Integer getLeaseId() {
        return leaseId;
    }

    public void setLeaseId(Integer leaseId) {
        this.leaseId = leaseId;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;

        Payment payment = (Payment) o;

        if (customer != null ? !customer.equals(payment.customer) : payment.customer != null) return false;
        if (leaseId != null ? !leaseId.equals(payment.leaseId) : payment.leaseId != null) return false;
        if (paymentId != null ? !paymentId.equals(payment.paymentId) : payment.paymentId != null) return false;
        if (cost != null ? !cost.equals(payment.cost) : payment.cost != null) return false;
        if (paymentStatus != payment.paymentStatus) return false;
        if (month != null ? !month.equals(payment.month) : payment.month != null) return false;
        return year != null ? year.equals(payment.year) : payment.year == null;
    }

    @Override
    public int hashCode() {
        int result = customer != null ? customer.hashCode() : 0;
        result = 31 * result + (leaseId != null ? leaseId.hashCode() : 0);
        result = 31 * result + (paymentId != null ? paymentId.hashCode() : 0);
        return result;
    }
}
