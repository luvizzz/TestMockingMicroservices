package steps;

import domain.leaseService.Lease;
import domain.leaseService.Payment;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class LeaseServiceSteps {

    private String baseUri;

    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    //@Step
    public Response createLease(String customer, Lease body) {
        return RestAssured.given()
                .baseUri(baseUri)
                .contentType("application/json")
                .body(body)
                .when()
                .log().all()
                .post("/customer/{customer}/lease", customer)
                .then()
                .log().all()
                .extract().response();
    }

    //@Step
    public Response createPayment(String customer, Integer leaseId, Payment body) {
        return RestAssured.given()
                .baseUri(baseUri)
                .contentType("application/json")
                .body(body)
                .when()
                .log().all()
                .post("/customer/{customer}/lease/{leaseId}", customer, leaseId)
                .then()
                .log().all()
                .extract().response();
    }

    //@Step
    public Response getAllLeases(String customer) {
        return RestAssured.given()
                .baseUri(baseUri)
                .when()
                .log().all()
                .get("/customer/{customer}/lease", customer)
                .then()
                .log().all()
                .extract().response();
    }

    //@Step
    public Response getMostRecentLease(String customer) {
        return RestAssured.given()
                .baseUri(baseUri)
                .when()
                .log().all()
                .get("/customer/{customer}/lease/mostRecent", customer)
                .then()
                .log().all()
                .extract().response();
    }

    //@Step
    public Response getAllPayments(String customer, Integer leaseId) {
        return RestAssured.given()
                .baseUri(baseUri)
                .when()
                .log().all()
                .get("/customer/{customer}/lease/{leaseId}/payment", customer, leaseId)
                .then()
                .log().all()
                .extract().response();
    }

    //@Step
    public Response deleteAllLeases(String customer) {
        return RestAssured.given()
                .baseUri(baseUri)
                .when()
                .log().all()
                .delete("/customer/{customer}/lease", customer)
                .then()
                .log().all()
                .extract().response();
    }

    //@Step
    public Response deleteSpecificLease(String customer, Integer leaseId) {
        return RestAssured.given()
                .baseUri(baseUri)
                .when()
                .log().all()
                .delete("/customer/{customer}/lease/{leaseId}", customer, leaseId)
                .then()
                .log().all()
                .extract().response();
    }

    //@Step
    public Response deleteAllPayments(String customer, Integer leaseId) {
        return RestAssured.given()
                .baseUri(baseUri)
                .when()
                .log().all()
                .delete("/customer/{customer}/lease/{leaseId}/payment", customer, leaseId)
                .then()
                .log().all()
                .extract().response();
    }

    //@Step
    public Response deleteSpecificPayment(String customer, Integer leaseId, Integer paymentId) {
        return RestAssured.given()
                .baseUri(baseUri)
                .when()
                .log().all()
                .delete("/customer/{customer}/lease/{leaseId}/payment/{paymentId}", customer, leaseId, paymentId)
                .then()
                .log().all()
                .extract().response();
    }
}
