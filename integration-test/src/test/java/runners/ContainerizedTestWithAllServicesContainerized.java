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
import static utils.TestDataRandom.randomCarId;
import static utils.TestDataRandom.randomCustomerId;

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
    public void createLeaseAndRent_expectCustomerServiceToPresentBoth() {
        String customer = randomCustomerId();
        Rent rent = new Rent.Builder()
                .withCost(10.0f)
                .withCarId(randomCarId())
                .withPaymentStatus(PaymentStatus.COMPLETE)
                .withRentalStatus(RentalStatus.FINISHED)
                .withStartDate(formatToRentalServiceDate(DateTime.now().minusYears(1)))
                .withEndDate(formatToRentalServiceDate(DateTime.now().plusYears(1)))
                .build();
        Response rentalResponse = rentalServiceSteps.createRental(customer, rent);
        Assumptions.assumeTrue(rentalResponse.statusCode() == SC_CREATED, "Rent created");
        Rent createdRent = rentalResponse.as(Rent.class);

        Lease lease = new Lease.Builder()
                .withCarId(randomCarId())
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

    @Test
    public void createTwoRents_expectCustomerServiceToPresentBoth() {
        String customer = randomCustomerId();
        Rent firstRent = new Rent.Builder()
                .withCost(10.0f)
                .withCarId(randomCarId())
                .withPaymentStatus(PaymentStatus.COMPLETE)
                .withRentalStatus(RentalStatus.ONGOING)
                .withStartDate(formatToRentalServiceDate(DateTime.now().minusYears(1)))
                .withEndDate(formatToRentalServiceDate(DateTime.now().plusYears(1)))
                .build();
        Response firstRentalResponse = rentalServiceSteps.createRental(customer, firstRent);
        Assumptions.assumeTrue(firstRentalResponse.statusCode() == SC_CREATED, "First rent created");
        Rent createdFirstRent = firstRentalResponse.as(Rent.class);

        Rent secondRent = new Rent.Builder()
                .withCost(10.0f)
                .withCarId(randomCarId())
                .withPaymentStatus(PaymentStatus.COMPLETE)
                .withRentalStatus(RentalStatus.FINISHED)
                .withStartDate(formatToRentalServiceDate(DateTime.now().minusMonths(2)))
                .withEndDate(formatToRentalServiceDate(DateTime.now().minusMonths(1)))
                .build();
        Response secondRentalResponse = rentalServiceSteps.createRental(customer, secondRent);
        Assumptions.assumeTrue(secondRentalResponse.statusCode() == SC_CREATED, "Second rent created");
        Rent createdSecondRent = secondRentalResponse.as(Rent.class);

        Response allHistory = customerServiceSteps.getAllHistory(customer);
        Response mostRecent = customerServiceSteps.getMostRecent(customer);

        customerServiceSteps.assertAllHistory(allHistory, List.of(createdFirstRent, createdSecondRent), List.of());
        customerServiceSteps.assertMostRecent(mostRecent, createdSecondRent, null);
    }

    @Test
    public void createTwoLeases_expectCustomerServiceToPresentBoth() {
        String customer = randomCustomerId();
        Lease firstLease = new Lease.Builder()
                .withCarId(randomCarId())
                .withLeaseStatus(LeaseStatus.ONGOING)
                .withStartDate(formatToLeaseServiceDate(DateTime.now().minusYears(1)))
                .withEndDate(formatToLeaseServiceDate(DateTime.now().plusYears(1)))
                .build();
        Response firstLeaseResponse = leaseServiceSteps.createLease(customer, firstLease);
        Assumptions.assumeTrue(firstLeaseResponse.statusCode() == SC_CREATED, "First lease created");
        Lease createdFirstLease = firstLeaseResponse.as(Lease.class);

        Lease secondLease = new Lease.Builder()
                .withCarId(randomCarId())
                .withLeaseStatus(LeaseStatus.ONGOING)
                .withStartDate(formatToLeaseServiceDate(DateTime.now().minusMonths(8)))
                .withEndDate(formatToLeaseServiceDate(DateTime.now().plusYears(2)))
                .build();
        Response secondLeaseResponse = leaseServiceSteps.createLease(customer, secondLease);
        Assumptions.assumeTrue(secondLeaseResponse.statusCode() == SC_CREATED, "Second lease created");
        Lease createdSecondLease = secondLeaseResponse.as(Lease.class);

        Response allHistory = customerServiceSteps.getAllHistory(customer);
        Response mostRecent = customerServiceSteps.getMostRecent(customer);

        customerServiceSteps.assertAllHistory(allHistory, List.of(), List.of(createdFirstLease, createdSecondLease));
        customerServiceSteps.assertMostRecent(mostRecent, null, createdSecondLease);
    }
}
