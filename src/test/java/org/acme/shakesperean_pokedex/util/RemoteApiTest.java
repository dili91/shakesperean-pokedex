package org.acme.shakesperean_pokedex.util;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.acme.shakesperean_pokedex.util.Configuration.MOCK_SERVER_PORT_NUMBER;

import com.github.tomakehurst.wiremock.WireMockServer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;


/**
 * todo: removed junit extension due to bugs introduced recently on quarkus
 * @see https://github.com/quarkusio/quarkus/issues/7199
 */
public abstract class RemoteApiTest {

    // holds the mock server for this api
    protected static WireMockServer mockServer;

    @BeforeAll
    public static void init() {
        mockServer = new WireMockServer(options().port(MOCK_SERVER_PORT_NUMBER));
        mockServer.start();
    }

    @AfterAll
    public static void afterAll() {
        mockServer.stop();
    }

    @BeforeEach
    public void reset() {
        mockServer.resetAll();
    }
}
