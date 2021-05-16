package app.controller;

import app.domain.Rent;
import app.domain.RentId;
import app.resource.RentRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
@RequestMapping("/")
@Api(value = "Rent Management service")
public class RentController extends BaseController{
    private static final Logger LOG = Logger.getLogger(RentController.class.getSimpleName());

    @Autowired
    RentRepository repository;

    public RentController(RentRepository repository) {
        this.repository = repository;
        path = "/";
    }

    @GetMapping(value = "/customer/{customer}/rent")
    @ApiOperation(value = "Get all rents for a specific customer")
    public ResponseEntity<List<Rent>> getRents(
            @PathVariable("customer") String customer
    ) {
        LOG.info(String.format("(getRents) for customer: %s", customer));

        List<Rent> rents = repository.findAllByCustomer(customer);
        return ResponseEntity.ok(rents);
    }

    @GetMapping(value = "/customer/{customer}/rent/mostRecent")
    @ApiOperation(value = "Get most recent rent for a specific customer")
    public ResponseEntity<Rent> getMostRecentRent(
            @PathVariable("customer") String customer
    ) {
        LOG.info(String.format("(getMostRecentRent) for customer: %s", customer));

        List<Rent> rents = repository.findAllByCustomer(customer);
        Optional<Rent> mostRecent = rents.stream().max(Comparator.comparing(Rent::getStartDate));
        return mostRecent.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping(value = "/customer/{customer}/rent")
    @ApiOperation(value = "Creates a new rent for a specific customer")
    public ResponseEntity<String> postRent(
            @PathVariable("customer") String customer,
            @RequestBody Rent rent) {
        LOG.info(String.format("(postRent) For customer %s input rent: %s", customer, rent.toJsonString()));

        if (rent.getCustomer() != null) {
            return createErrorResponse(CUSTOMER_PROVIDED_IN_BODY_MSG);
        }

        if (rent.getRentalId() != null) {
            return createErrorResponse(RENTAL_ID_PROVIDED_IN_BODY_MSG);
        }

        if (rent.getCarId() == null || rent.getStartDate() == null || rent.getEndDate() == null) {
            return createErrorResponse(INVALID_DEPENDENCY_MSG);
        }
        rent.setCustomer(customer);

        Rent savedRent = repository.save(rent);

        URI location = URI.create(String.format("/%s/rent/%s", savedRent.getCustomer(), savedRent.getRentalId()));
        return ResponseEntity.created(location).contentType(APPLICATION_JSON).body(savedRent.toJsonString());
    }

    @DeleteMapping(value = "/customer/{customer}/rent/{rentalId}")
    @ApiOperation(value = "Deletes a rent for a specific customer")
    public ResponseEntity<String> deleteRental(
            @PathVariable("customer") String customer,
            @PathVariable("rentalId") Long rentalId) {
        LOG.info(String.format("(deleteRental) Input customer: %s; Input rentalId: %d", customer, rentalId));

        RentId rentId = new RentId(customer, rentalId);
        Optional<Rent> maybeRent = repository.findById(rentId);

        if (maybeRent.isEmpty()) {
            return createErrorResponse(ENTITY_DOES_NOT_EXIST_MSG);
        } else {
            repository.deleteById(rentId);
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping(value = "/customer/{customer}/rent")
    @ApiOperation(value = "Deletes all rents for a specific customer")
    public ResponseEntity<String> deleteAllRentals(
            @PathVariable("customer") String customer) {
        LOG.info(String.format("(deleteAllRentals) Input customer: %s", customer));

        List<Rent> rents = repository.findAllByCustomer(customer);
        repository.deleteAll(rents);
        return ResponseEntity.noContent().build();
    }
}
