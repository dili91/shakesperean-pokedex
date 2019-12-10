package org.acme.shakesperean_pokedex.util;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.extension.*;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

//todo: remove if not used
public class MockApiExtension implements AfterEachCallback, BeforeAllCallback, AfterAllCallback, ParameterResolver {

    private WireMockServer wireMockServer;

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        wireMockServer.resetAll();
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        wireMockServer = new WireMockServer(options().dynamicPort());

        wireMockServer.startRecording("https://pokeapi.co");

        wireMockServer.start();
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        wireMockServer.stop();
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(WireMockServer.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return wireMockServer;
    }

}