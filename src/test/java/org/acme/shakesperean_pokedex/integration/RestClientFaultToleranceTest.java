package org.acme.shakesperean_pokedex.integration;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.shakesperean_pokedex.service.connector.FunTranslationsApiClient;
import org.acme.shakesperean_pokedex.util.RemoteApiTest;
import org.eclipse.microprofile.faulttolerance.exceptions.CircuitBreakerOpenException;
import org.eclipse.microprofile.faulttolerance.exceptions.TimeoutException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.stream.IntStream;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.lang.Thread.sleep;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.acme.shakesperean_pokedex.common.RestClientConfiguration.CIRCUIT_BREAKER_DELAY;
import static org.acme.shakesperean_pokedex.common.RestClientConfiguration.CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD;
import static org.acme.shakesperean_pokedex.util.Configuration.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Rest client fault tolerance test")
@QuarkusTest
public class RestClientFaultToleranceTest extends RemoteApiTest {

    //dummies
    private static final String A_TEXT_IN_MODERN_ENGLISH_LANGUAGE = "today sun is shining";
    private static final String A_TEXT_IN_SHAKESPEAREAN_LANGUAGE = "The present day travelling lamp is shining";

    //system under test
    @Inject
    @RestClient
    FunTranslationsApiClient funTranslationsApiClient;

    @Test
    @DisplayName("should get a timeout exception")
    public void shouldGetATimeoutException() {
        //given
        mockServer.stubFor(post(urlPathMatching(TRANSLATION_API_PATH))
                .willReturn(ok()
                        .withFixedDelay(RESPONSE_DELAYS_MS)
                )
        );

        //expect
        assertThrows(TimeoutException.class,
                () -> funTranslationsApiClient.translate(A_TEXT_IN_MODERN_ENGLISH_LANGUAGE));
    }

    @Test
    @DisplayName("Trivial test to check that rest clients are resilient to external server failures")
    public void shouldTestFaultTolerance() throws InterruptedException {
        //remote server initially down
        mockServer.stubFor(post(urlPathMatching(TRANSLATION_API_PATH))
                .willReturn(serviceUnavailable())
        );

        //check that client gets only WebApplicationException exception for the first 10 calls
        IntStream.range(0, CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD)
                .forEach(index -> {
                    assertThrows(Exception.class,
                            () -> funTranslationsApiClient.translate(A_TEXT_IN_MODERN_ENGLISH_LANGUAGE));
                });

        //check that client gets a circuit breaker exception, once the circuit is open
        assertThrows(CircuitBreakerOpenException.class,
                () -> funTranslationsApiClient.translate(A_TEXT_IN_MODERN_ENGLISH_LANGUAGE));

        //remote server becomes available
        mockServer.stubFor(post(urlPathEqualTo(TRANSLATION_API_PATH))
                .willReturn(ok()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .withBodyFile(TRANSLATION_API_SUN_IS_SHINING_JSON_FILE)
                )
        );

        //wait until the circuit reach a half-open state
        sleep(CIRCUIT_BREAKER_DELAY);

        // check that client now return a positive response
        IntStream.range(0, CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD)
                .forEach(index -> {
                    assertEquals(A_TEXT_IN_SHAKESPEAREAN_LANGUAGE, funTranslationsApiClient
                            .translate(A_TEXT_IN_MODERN_ENGLISH_LANGUAGE).getContents().getTranslated());
                });

    }
}
