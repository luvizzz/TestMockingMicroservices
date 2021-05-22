package base;

import org.junit.Rule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
public class BaseMockedTest {
    private final static Logger LOG = Logger.getLogger(BaseMockedTest.class.getSimpleName());

    String CUSTOMER_SERVICE_IMAGE_NAME = "customer-service-1.0";
    String DEFAULT_APPLICATION_PORT = "8080";
    DockerImageName MOCKSERVER_IMAGE = DockerImageName.parse("mockserver/mockserver");

    protected MockedApp customerServiceApplication = new MockedApp(CUSTOMER_SERVICE_IMAGE_NAME);

    @Rule
    public MockServerContainer leaseServiceMock = new MockServerContainer(MOCKSERVER_IMAGE);

    @Rule
    public MockServerContainer rentalServiceMock = new MockServerContainer(MOCKSERVER_IMAGE);

    @BeforeAll
    protected void init() {
        startApplications();
    }

    @AfterAll
    protected void tearDown() {
        stopApplications();
    }

    private void startApplications() {
        rentalServiceMock.start();
        leaseServiceMock.start();

        Map<String, String> customerServiceEnv = new HashMap<>();
        customerServiceEnv.put("RENTAL-SERVICE.URI", String.format("http://host.docker.internal:%d", rentalServiceMock.getServerPort()));
        customerServiceEnv.put("LEASE-SERVICE.URI", String.format("http://host.docker.internal:%d", leaseServiceMock.getServerPort()));
        customerServiceEnv.put("SERVER.PORT", DEFAULT_APPLICATION_PORT);
        customerServiceApplication.configure(customerServiceEnv);
        customerServiceApplication.start();
        customerServiceApplication.setAppUri(String.format("http://localhost:%d", customerServiceApplication.getPort()));
    }

    private void stopApplications() {
        customerServiceApplication.stop();
        leaseServiceMock.stop();
        rentalServiceMock.stop();
    }
}