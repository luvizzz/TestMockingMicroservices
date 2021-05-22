package domain.rentalService;

import com.fasterxml.jackson.annotation.JsonInclude;
import domain.PaymentStatus;
import org.springframework.lang.Nullable;

import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL) 	//  ignore all null fields
public class Rent {
    @Nullable
    private String customer;

    @Nullable
    private Integer rentalId;

    @Nullable
    private String carId;

    @Nullable
    private Float cost;

    @Nullable
    private PaymentStatus paymentStatus;

    @Nullable
    private RentalStatus rentalStatus;

    @Nullable
    private String startDate;

    @Nullable
    private String endDate;

    public static class Builder {
        private String customer;
        private Integer rentalId;
        private String carId;
        private Float cost;
        private PaymentStatus paymentStatus;
        private RentalStatus rentalStatus;
        private String startDate;
        private String endDate;

        public Builder() { }

        public Builder withCustomer(String customer) {
            this.customer = customer;
            return this;
        }

        public Builder withRentalId(Integer rentalId) {
            this.rentalId = rentalId;
            return this;
        }

        public Builder withCarId(String carId) {
            this.carId = carId;
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

        public Builder withRentalStatus(RentalStatus rentalStatus) {
            this.rentalStatus = rentalStatus;
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

        public Rent build() {
            Rent rent = new Rent();
            Optional.ofNullable(this.customer).ifPresent(value -> rent.customer = value);
            Optional.ofNullable(this.rentalId).ifPresent(value -> rent.rentalId = value);
            Optional.ofNullable(this.carId).ifPresent(value -> rent.carId = value);
            Optional.ofNullable(this.cost).ifPresent(value -> rent.cost = value);
            Optional.ofNullable(this.paymentStatus).ifPresent(value -> rent.paymentStatus = value);
            Optional.ofNullable(this.rentalStatus).ifPresent(value -> rent.rentalStatus = value);
            Optional.ofNullable(this.startDate).ifPresent(value -> rent.startDate = value);
            Optional.ofNullable(this.endDate).ifPresent(value -> rent.endDate = value);
            return rent;
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
    public Integer getRentalId() {
        return rentalId;
    }

    public void setRentalId(@Nullable Integer rentalId) {
        this.rentalId = rentalId;
    }

    @Nullable
    public String getCarId() {
        return carId;
    }

    public void setCarId(@Nullable String carId) {
        this.carId = carId;
    }

    @Nullable
    public Float getCost() {
        return cost;
    }

    public void setCost(@Nullable Float cost) {
        this.cost = cost;
    }

    @Nullable
    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(@Nullable PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Nullable
    public RentalStatus getRentalStatus() {
        return rentalStatus;
    }

    public void setRentalStatus(@Nullable RentalStatus rentalStatus) {
        this.rentalStatus = rentalStatus;
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
        if (!(o instanceof Rent)) return false;

        Rent rent = (Rent) o;

        if (customer != null ? !customer.equals(rent.customer) : rent.customer != null) return false;
        if (rentalId != null ? !rentalId.equals(rent.rentalId) : rent.rentalId != null) return false;
        if (carId != null ? !carId.equals(rent.carId) : rent.carId != null) return false;
        if (cost != null ? !cost.equals(rent.cost) : rent.cost != null) return false;
        if (paymentStatus != rent.paymentStatus) return false;
        if (rentalStatus != rent.rentalStatus) return false;
        if (startDate != null ? !startDate.equals(rent.startDate) : rent.startDate != null) return false;
        return endDate != null ? endDate.equals(rent.endDate) : rent.endDate == null;
    }

    @Override
    public int hashCode() {
        int result = customer != null ? customer.hashCode() : 0;
        result = 31 * result + (rentalId != null ? rentalId.hashCode() : 0);
        return result;
    }
}
