package org.acme.shakesperean_pokedex.service.connector;

import org.acme.shakesperean_pokedex.common.dto.poke_api.PokemonSpecies;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static javax.ws.rs.core.HttpHeaders.USER_AGENT;
import static org.acme.shakesperean_pokedex.common.RestClientConfiguration.*;

/**
 * Connector for PokeApi API
 *
 * @see "https://pokeapi.co/"
 */
@RegisterRestClient
@Path("/api/v2")
public interface PokeApiClient {

    @GET
    @Path("/pokemon-species/{name}")
    @ClientHeaderParam(name = USER_AGENT, value = "MicroProfile Rest Client") //required to avoid 403 on PokeApi
    @Produces(MediaType.APPLICATION_JSON)
    @Timeout(TIMEOUT)
    @CircuitBreaker(
            failureRatio = CIRCUIT_BREAKER_FAILURE_RATIO,
            requestVolumeThreshold = CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD,
            delay = CIRCUIT_BREAKER_DELAY
    )
    PokemonSpecies getPokemonSpecies(@PathParam("name") String name);
}
