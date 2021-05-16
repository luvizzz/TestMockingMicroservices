package app.controller;

import app.domain.Payment;
import app.domain.PaymentId;
import app.domain.PaymentStatus;
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
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
@RequestMapping("/")
@Api(value = "Payment Management service")
public class PaymentController extends BaseController {
    private static final Logger LOG = Logger.getLogger(LeaseController.class.getSimpleName());

    @Autowired
    PaymentRepository repository;

    public PaymentController(PaymentRepository repository) {
        this.repository = repository;
        path = "/";
    }

    @GetMapping(value = "/customer/{customer}/lease/{lease}/payment")
    @ApiOperation(value = "Get all payments for a specific lease")
    public ResponseEntity<List<Payment>> getPayments(
            @PathVariable("customer") String customer,
            @PathVariable("lease") Long lease
    ) {
        LOG.info(String.format("(getPayments) for customer '%s' and lease '%s'", customer, lease));

        List<Payment> payments = repository.findAllByCustomerAndLeaseId(customer, lease);
        return ResponseEntity.ok(payments);
    }

    @PostMapping(value = "/customer/{customer}/lease/{lease}/payment")
    @ApiOperation(value = "Creates a new payment for a specific lease")
    public ResponseEntity<String> postPayment(
            @PathVariable("customer") String customer,
            @PathVariable("lease") Long leaseId,
            @RequestBody Payment payment) {
        LOG.info(String.format("(postPayment) For customer %s and lease %s post input payment: %s", customer, leaseId, payment.toJsonString()));

        if (payment.getCustomer() != null) {
            return createErrorResponse(CUSTOMER_PROVIDED_IN_BODY_MSG);
        }

        if (payment.getLeaseId() != null) {
            return createErrorResponse(PLEASE_ID_PROVIDED_IN_BODY_MSG);
        }

        if (payment.getPaymentId() != null) {
            return createErrorResponse(PAYMENT_ID_PROVIDED_IN_BODY_MSG);
        }

        if (payment.getMonth() == 0 || payment.getYear() == 0) {
            return createErrorResponse(INVALID_DEPENDENCY_MSG);
        }

        if (payment.getPaymentStatus() == null) {
            payment.setPaymentStatus(PaymentStatus.PENDING);
        }

        payment.setCustomer(customer);
        payment.setLeaseId(leaseId);

        Payment savedPayment = repository.save(payment);

        URI location = URI.create(String.format("/%s/lease/%s/payment/%s", savedPayment.getCustomer(), savedPayment.getLeaseId(), savedPayment.getPaymentId()));
        return ResponseEntity.created(location).contentType(APPLICATION_JSON).body(savedPayment.toJsonString());
    }

    @DeleteMapping(value = "/customer/{customer}/lease/{leaseId}/payment/{paymentId}")
    @ApiOperation(value = "Deletes a payment for a specific lease")
    public ResponseEntity<String> deletePayment(
            @PathVariable("customer") String customer,
            @PathVariable("leaseId") Long leaseIdentifier,
            @PathVariable("paymentId") Long paymentIdentifier) {
        LOG.info(String.format("(deletePayment) Input customer: %s; Input leaseId: %d; Input paymentId: %d", customer, leaseIdentifier, paymentIdentifier));

        PaymentId paymentId = new PaymentId(customer, leaseIdentifier, paymentIdentifier);
        Optional<Payment> maybePayment = repository.findById(paymentId);

        if (maybePayment.isEmpty()) {
            return createErrorResponse(ENTITY_DOES_NOT_EXIST_MSG);
        } else {
            Payment payment = maybePayment.get();

            if (!payment.getCustomer().equals(customer)) {
                return createErrorResponse(PAYMENT_CUSTOMER_MISMATCH_MSG);
            }

            if (!payment.getLeaseId().equals(leaseIdentifier)) {
                return createErrorResponse(PAYMENT_CUSTOMER_MISMATCH_MSG);
            }

            repository.deleteById(paymentId);
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping(value = "/customer/{customer}/lease/{leaseId}/payment")
    @ApiOperation(value = "Deletes all payments for a specific lease")
    public ResponseEntity<String> deleteAllPayments(
            @PathVariable("customer") String customer,
            @PathVariable("leaseId") Long leaseIdentifier) {
        LOG.info(String.format("(deleteAllPayments) Input customer: %s; Input lease: %d", customer, leaseIdentifier));

        List<Payment> payments = repository.findAllByCustomerAndLeaseId(customer, leaseIdentifier);
        repository.deleteAll(payments);
        return ResponseEntity.noContent().build();
    }
}
