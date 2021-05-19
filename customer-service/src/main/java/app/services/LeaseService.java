package app.services;

import app.domain.leaseService.Lease;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.logging.Logger;

@Component
@Service("Lease")
public class LeaseService {
    private static final Logger LOG = Logger.getLogger(LeaseService.class.getSimpleName());

    private final RestTemplate restTemplate;

    @Value("${lease-service.uri}")
    private String serviceBaseUri;

    public LeaseService(RestTemplateBuilder builder) {
        this.restTemplate = builder.rootUri(serviceBaseUri).build();
    }

    public List<Lease> getLeases(String customer) {
        try {
            return restTemplate.getForObject(serviceBaseUri + "/customer/{customer}/lease", List.class, customer);
        } catch (RestClientException e) {
            LOG.info(String.format("Exception: %s - %s", e.getClass().getSimpleName(), e.getMessage()));
            return List.of();
        }
    }

    public Lease getMostRecentLease(String customer) {
        try {
            return restTemplate.getForObject(serviceBaseUri + "/customer/{customer}/lease/mostRecent", Lease.class, customer);
        } catch (RestClientException e) {
            LOG.info(String.format("Exception: %s - %s", e.getClass().getSimpleName(), e.getMessage()));
            return null;
        }
    }
}
