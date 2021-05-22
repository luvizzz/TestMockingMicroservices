package base;

import org.springframework.stereotype.Component;
import org.testcontainers.containers.GenericContainer;

import java.util.Map;
import java.util.logging.Logger;

@Component
public class MockedApp extends GenericContainer<MockedApp> {
    private final static Logger LOG = Logger.getLogger(MockedApp.class.getSimpleName());

    Integer DEFAULT_APPLICATION_PORT = 8080;

    private String APP_URI;

    protected void configure(Map<String, String> env) {
        this.withEnv(env);
        this.withExposedPorts(DEFAULT_APPLICATION_PORT);
    }

    public MockedApp(String imageName) {
        super(imageName);
    }

    public void start() {
        super.start();
    }

    public int getPort() {
        return super.getMappedPort(DEFAULT_APPLICATION_PORT);
    }

    public String getAppUri() {
        return APP_URI;
    }

    public void setAppUri(String APP_URI) {
        this.APP_URI = APP_URI;
    }
}
