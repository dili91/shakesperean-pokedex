package org.acme.shakesperean_pokedex.common;

/**
 * Rest client configuration class
 */
public final class RestClientConfiguration {

    private RestClientConfiguration() {
        // constant class...
    }

    /**
     * timeout in milliseconds
     **/
    public static final int TIMEOUT = 5000;

    /**
     * requests failure ratio. When this ratio is hit, the circuit is opened
     */
    public static final double CIRCUIT_BREAKER_FAILURE_RATIO = 0.4;

    /**
     * total number of requests on which the failure ratio is checked
     **/
    public static final int CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD = 10;

    /**
     * how long the circuit will stay open
     */
    public static final long CIRCUIT_BREAKER_DELAY = 3000L;
}
