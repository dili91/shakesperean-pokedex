package org.acme.shakesperean_pokedex.service;

import org.acme.shakesperean_pokedex.dto.poke_api.PokemonSpecies;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.FormParam;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@RegisterRestClient
public interface FunTranslationsApiClient {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    PokemonSpecies getPokemonSpecies(@FormParam("text") String text);
}
