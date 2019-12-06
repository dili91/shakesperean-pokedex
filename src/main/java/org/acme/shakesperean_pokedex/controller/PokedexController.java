package org.acme.shakesperean_pokedex.controller;

import org.acme.shakesperean_pokedex.service.PokeApiService;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/poke")
public class PokedexController {

    private final PokeApiService pokeApiService;

    public PokedexController(PokeApiService pokeApiService) {
        this.pokeApiService = pokeApiService;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return pokeApiService.getDescription("");
    }
}