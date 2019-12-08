package org.acme.shakesperean_pokedex.controller;

import org.acme.shakesperean_pokedex.dto.PokedexResult;
import org.acme.shakesperean_pokedex.service.ShakespeareanPokedexService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


@Path("/poke")
public class ShakespeareanPokedexController {

    @Inject
    ShakespeareanPokedexService shakespeareanPokedexService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PokedexResult getDescription(@QueryParam("name") String name) {
        return shakespeareanPokedexService.getDescription(name);
    }
}