package runners;

import base.BaseContainerizedTest;
import domain.PaymentStatus;
import domain.leaseService.Lease;
import domain.leaseService.LeaseStatus;
import domain.rentalService.Rent;
import domain.rentalService.RentalStatus;
import io.restassured.response.Response;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import steps.CustomerServiceSteps;
import steps.LeaseServiceSteps;
import steps.RentalServiceSteps;

import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static utils.CustomDateTimeFormatters.formatToLeaseServiceDate;
import static utils.CustomDateTimeFormatters.formatToRentalServiceDate;

public class ContainerizedTestWithAllServicesContainerized extends BaseContainerizedTest {

    RentalServiceSteps rentalServiceSteps = new RentalServiceSteps();

    LeaseServiceSteps leaseServiceSteps = new LeaseServiceSteps();

    CustomerServiceSteps customerServiceSteps = new CustomerServiceSteps();

    @BeforeAll
    public void init() {
        super.init();

        rentalServiceSteps.setBaseUri(rentalServiceApplication.getAppUri());
        leaseServiceSteps.setBaseUri(leaseServiceApplication.getAppUri());
        customerServiceSteps.setBaseUri(customerServiceApplication.getAppUri());
    }

    @Test
    public void myTest() {
        String customer = "my_customer";
        Rent rent = new Rent.Builder()
                .withCost(10.0f)
                .withCarId("My_rented_car")
                .withPaymentStatus(PaymentStatus.COMPLETE)
                .withRentalStatus(RentalStatus.FINISHED)
                .withStartDate(formatToRentalServiceDate(DateTime.now().minusYears(1)))
                .withEndDate(formatToRentalServiceDate(DateTime.now().plusYears(1)))
                .build();
        Response rentalResponse = rentalServiceSteps.createRental(customer, rent);
        Assumptions.assumeTrue(rentalResponse.statusCode() == SC_CREATED, "Rent created");
        Rent createdRent = rentalResponse.as(Rent.class);

        Lease lease = new Lease.Builder()
                .withCarId("My_leased_car")
                .withLeaseStatus(LeaseStatus.ONGOING)
                .withStartDate(formatToLeaseServiceDate(DateTime.now().minusYears(1)))
                .withEndDate(formatToLeaseServiceDate(DateTime.now().plusYears(1)))
                .build();
        Response leaseResponse = leaseServiceSteps.createLease(customer, lease);
        Assumptions.assumeTrue(leaseResponse.statusCode() == SC_CREATED, "Lease created");
        Lease createdLease = leaseResponse.as(Lease.class);

        Response allHistory = customerServiceSteps.getAllHistory(customer);
        Response mostRecent = customerServiceSteps.getMostRecent(customer);

        customerServiceSteps.assertAllHistory(allHistory, List.of(createdRent), List.of(createdLease));
        customerServiceSteps.assertMostRecent(mostRecent, createdRent, createdLease);
    }
}
