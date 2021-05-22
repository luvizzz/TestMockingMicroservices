package runners;

import base.BaseMockedTest;
import domain.PaymentStatus;
import domain.leaseService.Lease;
import domain.leaseService.LeaseStatus;
import domain.rentalService.Rent;
import domain.rentalService.RentalStatus;
import io.restassured.response.Response;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.MediaType;
import steps.CustomerServiceSteps;

import java.util.List;

import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.apache.http.HttpStatus.SC_OK;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;
import static utils.TestDataRandom.randomCarId;
import static utils.TestDataRandom.randomCustomerId;
import static utils.TestDataRandom.randomLeaseId;
import static utils.TestDataRandom.randomRentId;

public class TestWithServicesMocked extends BaseMockedTest {

    CustomerServiceSteps customerServiceSteps = new CustomerServiceSteps();

    @BeforeAll
    public void init() {
        super.init();

        customerServiceSteps.setBaseUri(customerServiceApplication.getAppUri());
    }

    @Test
    public void createLeaseAndRent_expectCustomerServiceToPresentBoth() {
        String customer = randomCustomerId();
        Rent rent = new Rent.Builder()
                .withCustomer(customer)
                .withRentalId(randomRentId())
                .withCarId(randomCarId())
                .withStartDate(DateTime.now().minusYears(1).toString())
                .withEndDate(DateTime.now().plusYears(1).toString())
                .withCost(10.0f)
                .withPaymentStatus(PaymentStatus.COMPLETE)
                .withRentalStatus(RentalStatus.FINISHED)
                .build();
        new MockServerClient(rentalServiceMock.getHost(), rentalServiceMock.getServerPort())
                .when(request()
                        .withPath(String.format("/customer/%s/rent", customer)))
                .respond(response()
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withStatusCode(SC_OK)
                        .withBody(json(List.of(rent))));
        new MockServerClient(rentalServiceMock.getHost(), rentalServiceMock.getServerPort())
                .when(request()
                        .withPath(String.format("/customer/%s/rent/mostRecent", customer)))
                .respond(response()
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withStatusCode(SC_OK)
                        .withBody(json(rent)));

        Lease lease = new Lease.Builder()
                .withCustomer(customer)
                .withLeaseId(randomLeaseId())
                .withCarId(randomCarId())
                .withStartDate(DateTime.now().minusYears(1).toString())
                .withEndDate(DateTime.now().plusYears(1).toString())
                .withLeaseStatus(LeaseStatus.ONGOING)
                .withPayments(List.of())
                .build();
        new MockServerClient(leaseServiceMock.getHost(), leaseServiceMock.getServerPort())
                .when(request()
                        .withPath(String.format("/customer/%s/lease", customer)))
                .respond(response()
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withStatusCode(SC_OK)
                        .withBody(json(List.of(lease))));
        new MockServerClient(leaseServiceMock.getHost(), leaseServiceMock.getServerPort())
                .when(request()
                        .withPath(String.format("/customer/%s/lease/mostRecent", customer)))
                .respond(response()
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withStatusCode(SC_OK)
                        .withBody(json(lease)));

        Response allHistory = customerServiceSteps.getAllHistory(customer);
        Response mostRecent = customerServiceSteps.getMostRecent(customer);

        customerServiceSteps.assertAllHistory(allHistory, List.of(rent), List.of(lease));
        customerServiceSteps.assertMostRecent(mostRecent, rent, lease);
    }


    @Test
    public void createTwoRents_expectCustomerServiceToPresentBoth() {
        String customer = randomCustomerId();
        Rent firstRent = new Rent.Builder()
                .withCustomer(customer)
                .withRentalId(randomRentId())
                .withCarId(randomCarId())
                .withStartDate(DateTime.now().minusYears(1).toString())
                .withEndDate(DateTime.now().plusYears(1).toString())
                .withCost(10.0f)
                .withPaymentStatus(PaymentStatus.COMPLETE)
                .withRentalStatus(RentalStatus.FINISHED)
                .build();

        Rent secondRent = new Rent.Builder()
                .withCustomer(customer)
                .withRentalId(randomRentId())
                .withCarId(randomCarId())
                .withStartDate(DateTime.now().minusMonths(2).toString())
                .withEndDate(DateTime.now().minusMonths(1).toString())
                .withCost(100.1f)
                .withPaymentStatus(PaymentStatus.COMPLETE)
                .withRentalStatus(RentalStatus.FINISHED)
                .build();

        new MockServerClient(rentalServiceMock.getHost(), rentalServiceMock.getServerPort())
                .when(request()
                        .withPath(String.format("/customer/%s/rent", customer)))
                .respond(response()
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withStatusCode(SC_OK)
                        .withBody(json(List.of(firstRent, secondRent))));
        new MockServerClient(rentalServiceMock.getHost(), rentalServiceMock.getServerPort())
                .when(request()
                        .withPath(String.format("/customer/%s/rent/mostRecent", customer)))
                .respond(response()
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withStatusCode(SC_OK)
                        .withBody(json(secondRent)));

        new MockServerClient(leaseServiceMock.getHost(), leaseServiceMock.getServerPort())
                .when(request()
                        .withPath(String.format("/customer/%s/lease", customer)))
                .respond(response()
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withStatusCode(SC_OK)
                        .withBody(json(List.of())));
        new MockServerClient(leaseServiceMock.getHost(), leaseServiceMock.getServerPort())
                .when(request()
                        .withPath(String.format("/customer/%s/lease/mostRecent", customer)))
                .respond(response()
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withStatusCode(SC_NO_CONTENT));

        Response allHistory = customerServiceSteps.getAllHistory(customer);
        Response mostRecent = customerServiceSteps.getMostRecent(customer);

        customerServiceSteps.assertAllHistory(allHistory, List.of(firstRent, secondRent), List.of());
        customerServiceSteps.assertMostRecent(mostRecent, secondRent, null);
    }

    @Test
    public void createTwoLeases_expectCustomerServiceToPresentBoth() {
        String customer = randomCustomerId();
        Lease firstLease = new Lease.Builder()
                .withCustomer(customer)
                .withLeaseId(randomLeaseId())
                .withCarId(randomCarId())
                .withStartDate(DateTime.now().minusYears(1).toString())
                .withEndDate(DateTime.now().plusYears(1).toString())
                .withPayments(List.of())
                .withLeaseStatus(LeaseStatus.FINISHED)
                .build();

        Lease secondLease = new Lease.Builder()
                .withCustomer(customer)
                .withLeaseId(randomLeaseId())
                .withCarId(randomCarId())
                .withStartDate(DateTime.now().minusMonths(2).toString())
                .withEndDate(DateTime.now().minusMonths(1).toString())
                .withPayments(List.of())
                .withLeaseStatus(LeaseStatus.FINISHED)
                .build();

        new MockServerClient(rentalServiceMock.getHost(), rentalServiceMock.getServerPort())
                .when(request()
                        .withPath(String.format("/customer/%s/rent", customer)))
                .respond(response()
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withStatusCode(SC_OK)
                        .withBody(json(List.of())));
        new MockServerClient(rentalServiceMock.getHost(), rentalServiceMock.getServerPort())
                .when(request()
                        .withPath(String.format("/customer/%s/rent/mostRecent", customer)))
                .respond(response()
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withStatusCode(SC_NO_CONTENT));

        new MockServerClient(leaseServiceMock.getHost(), leaseServiceMock.getServerPort())
                .when(request()
                        .withPath(String.format("/customer/%s/lease", customer)))
                .respond(response()
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withStatusCode(SC_OK)
                        .withBody(json(List.of(firstLease, secondLease))));
        new MockServerClient(leaseServiceMock.getHost(), leaseServiceMock.getServerPort())
                .when(request()
                        .withPath(String.format("/customer/%s/lease/mostRecent", customer)))
                .respond(response()
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withStatusCode(SC_OK)
                        .withBody(json(secondLease)));

        Response allHistory = customerServiceSteps.getAllHistory(customer);
        Response mostRecent = customerServiceSteps.getMostRecent(customer);

        customerServiceSteps.assertAllHistory(allHistory, List.of(), List.of(firstLease, secondLease));
        customerServiceSteps.assertMostRecent(mostRecent, null, secondLease);
    }
}
