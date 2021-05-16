package app.controller;

import app.domain.Customer;
import app.domain.CustomerMostRecent;
import app.domain.leaseService.Lease;
import app.domain.rentalService.Rent;
import app.services.LeaseService;
import app.services.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController("CustomerManagementService")
@RequestMapping("/")
public class CustomerController extends BaseController{
    private static final Logger LOG = Logger.getLogger(CustomerController.class.getSimpleName());

    @Autowired
    LeaseService leaseService;

    @Autowired
    RentalService rentalService;

    public CustomerController(LeaseService leaseService, RentalService rentalService) {
        path = "/";
    }

    @Autowired
    public void setLeaseService(LeaseService leaseService) {
        this.leaseService = leaseService;
    }

    @Autowired
    public void setRentalService(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping(value = "/customer/{customer}")
    public ResponseEntity<Customer> getCustomer(
            @PathVariable("customer") String customerId
    ) {
        LOG.info(String.format("(getCustomer) for customer: %s", customerId));

        List<Rent> rentals = rentalService.getRentals(customerId);
        List<Lease> leases = leaseService.getLeases(customerId);
        Customer customer = new Customer(rentals, leases);

        return ResponseEntity.ok(customer);
    }

    @GetMapping(value = "/customer/{customer}/mostRecent")
    public ResponseEntity<CustomerMostRecent> getMostRecent(
            @PathVariable("customer") String customerId
    ) {
        LOG.info(String.format("(getMostRecent) for customer: %s", customerId));

        CustomerMostRecent customer = new CustomerMostRecent(rentalService.getMostRecentRental(customerId), leaseService.getMostRecentLease(customerId));

        return ResponseEntity.ok(customer);
    }
}
