package org.acme.shakesperean_pokedex.controller;

import org.acme.shakesperean_pokedex.dto.PokedexResult;
import org.acme.shakesperean_pokedex.exception.TranslationException;
import org.acme.shakesperean_pokedex.service.ShakespeareanPokedexService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/pokemon")
public class ShakespeareanPokedexController {

    //todo rest exception handler for 404, validation, others....

    private final ShakespeareanPokedexService shakespeareanPokedexService;

    @Inject
    public ShakespeareanPokedexController(ShakespeareanPokedexService shakespeareanPokedexService) {
        this.shakespeareanPokedexService = shakespeareanPokedexService;
    }

    @GET
    @Path("/{pokemonName}")
    @Produces(MediaType.APPLICATION_JSON)
    public PokedexResult getDescription(@PathParam("pokemonName") String pokemonName) throws TranslationException {
        return shakespeareanPokedexService.getShakespeareanResult(pokemonName);
    }
}