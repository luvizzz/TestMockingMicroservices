package steps;

import domain.leaseService.Lease;
import domain.rentalService.Rent;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.logging.Logger;

public class CustomerServiceSteps {
    private final static Logger LOG = Logger.getLogger(CustomerServiceSteps.class.getSimpleName());

    private String baseUri;

    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    //@Step
    public Response getAllHistory(String customer) {
        return RestAssured.given()
                .baseUri(baseUri)
                .log().all()
                .when()
                .get("/customer/{customer}", customer)
                .then()
                .log().all()
                .extract().response();
    }

    //@Step
    public Response getMostRecent(String customer) {
        return RestAssured.given()
                .baseUri(baseUri)
                .log().all()
                .when()
                .get("/customer/{customer}/mostRecent", customer)
                .then()
                .log().all()
                .extract().response();
    }

    public void assertAllHistory(Response response, List<Rent> expectedRents, List<Lease> expectedLeases) {
        JsonPath responseJsonPath = response.getBody().jsonPath();
        assertListOfRents(expectedRents, responseJsonPath.getList("rentals", Rent.class));
        assertListOfLeases(expectedLeases, responseJsonPath.getList("leases", Lease.class));
    }

    public void assertMostRecent(Response response, Rent expectedRent, Lease expectedLease) {
        JsonPath responseJsonPath = response.getBody().jsonPath();
        assertSingleRent(expectedRent, responseJsonPath.getObject("rental", Rent.class));
        assertSingleLease(expectedLease, responseJsonPath.getObject("lease", Lease.class));
    }

    private void assertListOfRents(List<Rent> expected, List<Rent> actual) {
        LOG.info(String.format("Asserting List of rentals:%nExpected:%s%nActual:%s%n", expected.toString(), actual.toString()));

        Assertions.assertThat(actual)
                .as("Rental list contains all expected elements")
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    private void assertListOfLeases(List<Lease> expected, List<Lease> actual) {
        LOG.info(String.format("Asserting List of leases:%nExpected:%s%nActual:%s%n", expected.toString(), actual.toString()));

        Assertions.assertThat(actual)
                .as("Lease list contains all expected elements")
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    private void assertSingleRent(Rent expected, Rent actual) {
        LOG.info(String.format("Asserting single rental:%nExpected:%s%nActual:%s%n",
                expected != null ? expected.toString() : "null",
                actual != null ? actual.toString() : "null"));

        Assertions.assertThat(actual)
                .as("Rental matches expected")
                .isEqualTo(expected);
    }

    private void assertSingleLease(Lease expected, Lease actual) {
        LOG.info(String.format("Asserting single lease:%nExpected:%s%nActual:%s%n",
                expected != null ? expected.toString() : "null",
                actual != null ? actual.toString() : "null"));

        Assertions.assertThat(actual)
                .as("Lease matches expected")
                .isEqualTo(expected);
    }
}
