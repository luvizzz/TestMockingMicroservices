package app.controller;

import app.domain.Lease;
import app.domain.LeaseId;
import app.domain.LeaseStatus;
import app.resource.LeaseRepository;
import app.resource.PaymentRepository;
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
@Api(value = "Lease Management service")
public class LeaseController extends BaseController{
    private static final Logger LOG = Logger.getLogger(LeaseController.class.getSimpleName());

    @Autowired
    LeaseRepository repository;

    @Autowired
    PaymentRepository paymentRepository;

    public LeaseController(LeaseRepository repository) {
        this.repository = repository;
        path = "/";
    }

    @GetMapping(value = "/customer/{customer}/lease")
    @ApiOperation(value = "Get all leases for a specific customer")
    public ResponseEntity<List<Lease>> getLeases(
            @PathVariable("customer") String customer
    ) {
        LOG.info(String.format("(getLeases) for customer: %s", customer));

        List<Lease> leases = repository.findAllByCustomer(customer);
        leases.forEach(lease -> lease.setPayments(paymentRepository.findAllByCustomerAndLeaseId(lease.getCustomer(), lease.getLeaseId())));
        return ResponseEntity.ok(leases);
    }

    @GetMapping(value = "/customer/{customer}/lease/mostRecent")
    @ApiOperation(value = "Get most recent lease for a specific customer")
    public ResponseEntity<Lease> getMostRecentRent(
            @PathVariable("customer") String customer
    ) {
        LOG.info(String.format("(getMostRecentRent) for customer: %s", customer));

        List<Lease> leases = repository.findAllByCustomer(customer);
        Optional<Lease> mostRecent = leases.stream().max(Comparator.comparing(Lease::getStartDate));
        if (mostRecent.isPresent()) {
            Lease currentLease = mostRecent.get();
            currentLease.setPayments(paymentRepository.findAllByCustomerAndLeaseId(customer, currentLease.getLeaseId()));
            return ResponseEntity.ok(currentLease);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/customer/{customer}/lease")
    @ApiOperation(value = "Creates a new lease for a specific customer")
    public ResponseEntity<String> postLease(
            @PathVariable("customer") String customer,
            @RequestBody Lease lease) {
        LOG.info(String.format("(postLease) For customer %s input lease: %s", customer, lease.toJsonString()));

        if (lease.getCustomer() != null) {
            return createErrorResponse(CUSTOMER_PROVIDED_IN_BODY_MSG);
        }

        if (lease.getLeaseId() != null) {
            return createErrorResponse(PLEASE_ID_PROVIDED_IN_BODY_MSG);
        }

        if (lease.getCarId() == null || lease.getStartDate() == null || lease.getEndDate() == null) {
            return createErrorResponse(INVALID_DEPENDENCY_MSG);
        }

        if (lease.getPayments() != null) {
            return createErrorResponse(PAYMENT_NOT_ACCEPTED_MSG);
        }

        if (lease.getLeaseStatus() == null) {
            lease.setLeaseStatus(LeaseStatus.PLANNED);
        }

        lease.setCustomer(customer);

        Lease savedLease = repository.save(lease);

        URI location = URI.create(String.format("/%s/lease/%s", savedLease.getCustomer(), savedLease.getLeaseId()));
        return ResponseEntity.created(location).contentType(APPLICATION_JSON).body(savedLease.toJsonString());
    }

    @DeleteMapping(value = "/customer/{customer}/lease/{leaseId}")
    @ApiOperation(value = "Deletes a lease for a specific customer")
    public ResponseEntity<String> deleteLease(
            @PathVariable("customer") String customer,
            @PathVariable("leaseId") Long leaseIdentifier) {
        LOG.info(String.format("(deleteLease) Input customer: %s; Input leaseId: %d", customer, leaseIdentifier));

        LeaseId leaseId = new LeaseId(customer, leaseIdentifier);
        Optional<Lease> maybeLease = repository.findById(leaseId);

        if (maybeLease.isEmpty()) {
            return createErrorResponse(ENTITY_DOES_NOT_EXIST_MSG);
        } else {
            repository.deleteById(leaseId);
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping(value = "/customer/{customer}/lease")
    @ApiOperation(value = "Deletes all leases for a specific customer")
    public ResponseEntity<String> deleteAllLeases(
            @PathVariable("customer") String customer) {
        LOG.info(String.format("(deleteAllLeases) Input customer: %s", customer));

        List<Lease> leases = repository.findAllByCustomer(customer);
        repository.deleteAll(leases);
        return ResponseEntity.noContent().build();
    }
}
