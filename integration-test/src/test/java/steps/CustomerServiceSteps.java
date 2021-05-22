package steps;

import domain.leaseService.Lease;
import domain.rentalService.Rent;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

import java.util.List;

public class CustomerServiceSteps {

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

        Assertions.assertThat(responseJsonPath.getList("rentals", Rent.class))
                .as("Rental list contains all expected elements")
                .containsExactlyInAnyOrderElementsOf(expectedRents);

        Assertions.assertThat(responseJsonPath.getList("leases", Lease.class))
                .as("Lease list contains all expected elements")
                .containsExactlyInAnyOrderElementsOf(expectedLeases);
    }

    public void assertMostRecent(Response response, Rent expectedRent, Lease expectedLease) {
        JsonPath responseJsonPath = response.getBody().jsonPath();

        Assertions.assertThat(responseJsonPath.getObject("rental", Rent.class))
                .as("Rent is returned as expected")
                .isEqualTo(expectedRent);

        Assertions.assertThat(responseJsonPath.getObject("lease", Lease.class))
                .as("Lease is returned as expected")
                .isEqualTo(expectedLease);
    }
}
