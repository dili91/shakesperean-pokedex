package org.acme.shakesperean_pokedex.controller;

import org.acme.shakesperean_pokedex.service.PokeApiService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


@Path("/poke")
public class PokedexController {

    @Inject
    @RestClient
    PokeApiService pokeApiService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getDescription(@QueryParam("name") String name) {
        return pokeApiService.getPokemonSpecies(name).getName();
    }
}