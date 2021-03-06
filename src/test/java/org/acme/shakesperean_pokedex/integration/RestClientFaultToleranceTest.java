package org.acme.shakesperean_pokedex.integration;

import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.serviceUnavailable;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static java.lang.Thread.sleep;
import static java.util.stream.IntStream.range;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.acme.shakesperean_pokedex.common.RestClientConfiguration.CIRCUIT_BREAKER_DELAY;
import static org.acme.shakesperean_pokedex.common.RestClientConfiguration.CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD;
import static org.acme.shakesperean_pokedex.util.Configuration.RESPONSE_DELAYS_MS;
import static org.acme.shakesperean_pokedex.util.Configuration.TRANSLATION_API_PATH;
import static org.acme.shakesperean_pokedex.util.Configuration.TRANSLATION_API_SUN_IS_SHINING_JSON_STUB_FILE;
import static org.acme.shakesperean_pokedex.util.Util.getFunTranslationsJsonStubLocation;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.function.Supplier;

import javax.inject.Inject;

import org.acme.shakesperean_pokedex.common.dto.fun_translations.Translation;
import org.acme.shakesperean_pokedex.service.connector.FunTranslationsApiClient;
import org.acme.shakesperean_pokedex.util.RemoteApiTest;
import org.eclipse.microprofile.faulttolerance.exceptions.TimeoutException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@DisplayName("Rest client fault tolerance test")
@Disabled("Hystrix circuit breaker class not available anymore with last version of fault tolerance library")
@QuarkusTest
public class RestClientFaultToleranceTest extends RemoteApiTest {

    //dummies
    private static final String A_TEXT = "today sun is shining";

    //system under test
    @Inject
    @RestClient
    FunTranslationsApiClient funTranslationsApiClient;

    private Supplier<Translation> remoteCall = () -> funTranslationsApiClient
            .translate(A_TEXT);

    /**
     * Executes an api call and ignore exceptions
     */
    private Runnable remoteCallWithSuppressedExceptions = () -> {
        try {
            remoteCall.get();
        } catch (Throwable e) {
            System.out.println("ignored exception while calling remote server: " + e.getClass());
        }
    };

    @BeforeEach
    public void reset() {
        // Optional.ofNullable(getCircuitBreaker()).ifPresent(HystrixCircuitBreaker::markSuccess);
    }

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
        assertThrows(TimeoutException.class, () -> remoteCall.get());
    }

    @Test
    @DisplayName("test to check that rest clients are resilient to external server failures")
    public void shouldTestFaultTolerance() throws InterruptedException {
        //check that circuit breaker is not set
        // assertFalse(getCircuitBreaker().isOpen());

        //remote server initially down
        mockServer.stubFor(post(urlPathMatching(TRANSLATION_API_PATH)).willReturn(serviceUnavailable()));

        //check that circuit breaker gets opened after the first CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD calls
        range(0, CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD)
                .forEach(index -> remoteCallWithSuppressedExceptions.run());
        // assertTrue(getCircuitBreaker().isOpen());

        //remote server becomes available
        mockServer.stubFor(post(urlPathEqualTo(TRANSLATION_API_PATH))
                .willReturn(ok()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .withBodyFile(getFunTranslationsJsonStubLocation(TRANSLATION_API_SUN_IS_SHINING_JSON_STUB_FILE))
                )
        );

        //wait until the circuit reach a half-open state
        sleep(CIRCUIT_BREAKER_DELAY);

        // check that a positive response reopens the circuit
        remoteCallWithSuppressedExceptions.run();
        // assertFalse(getCircuitBreaker().isOpen());
    }

    /**
     * Return the circuit breaker for translate command
     *
     * @return the circuit breaker
     */
    // private HystrixCircuitBreaker getCircuitBreaker() {
    //     return Util.getCircuitBreaker(FunTranslationsApiClient.class, "translate");
    // }
}
