package steps;

import domain.rentalService.Rent;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RentalServiceSteps {

    private String baseUri;

    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    //@Step
    public Response createRental(String customer, Rent body) {
        return RestAssured.given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .log().all()
                .post("/customer/{customer}/rent", customer)
                .then()
                .log().all()
                .extract().response();
    }

    //@Step
    public Response getAllRentals(String customer) {
        return RestAssured.given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .when()
                .log().all()
                .get("/customer/{customer}/rent", customer)
                .then()
                .log().all()
                .extract().response();
    }

    //@Step
    public Response getMostRecentRental(String customer) {
        return RestAssured.given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .when()
                .log().all()
                .get("/customer/{customer}/rent/mostRecent", customer)
                .then()
                .log().all()
                .extract().response();
    }

    //@Step
    public Response deleteAllRentals(String customer) {
        return RestAssured.given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .when()
                .log().all()
                .delete("/customer/{customer}/rent", customer)
                .then()
                .log().all()
                .extract().response();
    }

    //@Step
    public Response deleteSpecificRental(String customer, Integer rentalId) {
        return RestAssured.given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .when()
                .log().all()
                .delete("/customer/{customer}/rent/{rentalId}", customer, rentalId)
                .then()
                .log().all()
                .extract().response();
    }
}
