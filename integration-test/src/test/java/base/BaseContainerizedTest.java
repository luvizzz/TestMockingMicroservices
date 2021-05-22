package base;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
public class BaseContainerizedTest {
    private final static Logger LOG = Logger.getLogger(BaseContainerizedTest.class.getSimpleName());

    String DB_IMAGE = "postgres:9.6.12";
    String DB_NAME = "postgres";
    String DB_USER = "postgres";
    String DB_PASSWORD = "postgres";
    String JDBC_URL = "jdbc:postgresql://host.docker.internal:%d/%s";
    //String JDBC_URL = "jdbc:tc:postgresql://host.docker.internal:%d/%s";
    String CUSTOMER_SERVICE_IMAGE_NAME = "customer-service-1.0";
    String LEASE_SERVICE_IMAGE_NAME = "lease-service-1.0";
    String RENTAL_SERVICE_IMAGE_NAME = "rental-service-1.0";
    String DEFAULT_APPLICATION_PORT = "8080";

    protected MockedDb rentalServiceDatabase = new MockedDb(DB_IMAGE);

    protected MockedDb leaseServiceDatabase = new MockedDb(DB_IMAGE);

    protected MockedApp customerServiceApplication = new MockedApp(CUSTOMER_SERVICE_IMAGE_NAME);

    protected MockedApp leaseServiceApplication = new MockedApp(LEASE_SERVICE_IMAGE_NAME);

    protected MockedApp rentalServiceApplication = new MockedApp(RENTAL_SERVICE_IMAGE_NAME);

    @BeforeAll
    protected void init() {
        startDatabases();
        startApplications();
    }

    @AfterAll
    protected void tearDown() {
        stopApplications();
        stopDatabases();
    }

    private void startDatabases() {
        rentalServiceDatabase.configure(DB_NAME, DB_USER, DB_PASSWORD);
        rentalServiceDatabase.start();
        LOG.info("rentalServiceDatabase port: " + rentalServiceDatabase.getPort());

        leaseServiceDatabase.configure(DB_NAME, DB_USER, DB_PASSWORD);
        leaseServiceDatabase.start();
        LOG.info("leaseServiceDatabase port: " + leaseServiceDatabase.getPort());
    }

    private void stopDatabases() {
        rentalServiceDatabase.stop();
        leaseServiceDatabase.stop();
    }

    private void startApplications() {
        Map<String, String> rentalServiceEnv = new HashMap<>();
        rentalServiceEnv.put("SPRING.DATASOURCE.URL", setupJdbcUrl(rentalServiceDatabase));
        rentalServiceEnv.put("SERVER.PORT", DEFAULT_APPLICATION_PORT);
        rentalServiceApplication.configure(rentalServiceEnv);
        rentalServiceApplication.start();
        rentalServiceApplication.setAppUri(String.format("http://localhost:%d", rentalServiceApplication.getPort()));

        Map<String, String> leaseServiceEnv = new HashMap<>();
        leaseServiceEnv.put("SPRING.DATASOURCE.URL", setupJdbcUrl(leaseServiceDatabase));
        leaseServiceEnv.put("SERVER.PORT", DEFAULT_APPLICATION_PORT);
        leaseServiceApplication.configure(leaseServiceEnv);
        leaseServiceApplication.start();
        leaseServiceApplication.setAppUri(String.format("http://localhost:%d", leaseServiceApplication.getPort()));

        Map<String, String> customerServiceEnv = new HashMap<>();
        customerServiceEnv.put("RENTAL-SERVICE.URI", String.format("http://host.docker.internal:%d", rentalServiceApplication.getPort()));
        customerServiceEnv.put("LEASE-SERVICE.URI", String.format("http://host.docker.internal:%d", leaseServiceApplication.getPort()));
        customerServiceEnv.put("SERVER.PORT", DEFAULT_APPLICATION_PORT);
        customerServiceApplication.configure(customerServiceEnv);
        customerServiceApplication.start();
        customerServiceApplication.setAppUri(String.format("http://localhost:%d", customerServiceApplication.getPort()));
    }

    private void stopApplications() {
        customerServiceApplication.stop();
        leaseServiceApplication.stop();
        rentalServiceApplication.stop();
    }

    private String setupJdbcUrl(MockedDb database) {
        return String.format(JDBC_URL, database.getPort(), DB_NAME);
    }
}