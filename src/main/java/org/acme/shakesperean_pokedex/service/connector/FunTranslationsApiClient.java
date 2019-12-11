package org.acme.shakesperean_pokedex.service.connector;

import org.acme.shakesperean_pokedex.common.dto.fun_translations.Translation;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.FormParam;

import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static org.acme.shakesperean_pokedex.common.RestClientConfiguration.*;

@RegisterRestClient
public interface FunTranslationsApiClient {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Timeout(TIMEOUT)
    @CircuitBreaker(
            failureRatio = CIRCUIT_BREAKER_FAILURE_RATIO,
            requestVolumeThreshold = CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD,
            delay = CIRCUIT_BREAKER_DELAY
    )
    Translation translate(@FormParam("text") String text);
}
