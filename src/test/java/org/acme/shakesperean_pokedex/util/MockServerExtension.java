package org.acme.shakesperean_pokedex.util;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.extension.*;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.acme.shakesperean_pokedex.util.Configuration.MOCK_SERVER_PORT_NUMBER;

public class MockServerExtension implements AfterEachCallback, BeforeAllCallback, AfterAllCallback, TestInstancePostProcessor {

    private WireMockServer wireMockServer;

    //todo: improve this to support dynamic ports
    public MockServerExtension() {
        wireMockServer = new WireMockServer(options().port(MOCK_SERVER_PORT_NUMBER));
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        wireMockServer.resetAll();
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        wireMockServer.start();
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) {
        wireMockServer.stop();
    }

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext extensionContext) throws Exception {
        testInstance.getClass()
                .getMethod("setMockServer", WireMockServer.class)
                .invoke(testInstance, wireMockServer);
    }
}