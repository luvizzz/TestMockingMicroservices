package app.services;

import app.domain.rentalService.Rent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.logging.Logger;

@Component
@Service("Rental")
public class RentalService {
    private static final Logger LOG = Logger.getLogger(RentalService.class.getSimpleName());

    private final RestTemplate restTemplate;

    @Value("${rental-service.uri}")
    private String serviceBaseUri;

    public RentalService(RestTemplateBuilder builder) {
        this.restTemplate = builder.rootUri(serviceBaseUri).build();
    }

    public List<Rent> getRentals(String customer) {
        return restTemplate.getForObject(serviceBaseUri + "/customer/{customer}/rent", List.class, customer);
    }

    public Rent getMostRecentRental(String customer) {
        return restTemplate.getForObject(serviceBaseUri + "/customer/{customer}/rent/mostRecent", Rent.class, customer);
    }
}
