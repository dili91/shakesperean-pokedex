package org.acme.shakesperean_pokedex.service.connector;

import org.acme.shakesperean_pokedex.dto.poke_api.PokemonSpecies;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RegisterRestClient
@Path("/api/v2")
public interface PokeApiClient {

    @GET
    @Path("/pokemon-species/{name}")
    @ClientHeaderParam(name = "User-Agent", value = "MicroProfile Rest Client") //required to avoid 403 on PokeApi
    @Produces(MediaType.APPLICATION_JSON)
    PokemonSpecies getPokemonSpecies(@PathParam("name") String name);
}
