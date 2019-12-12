package org.acme.shakesperean_pokedex.controller;

import org.acme.shakesperean_pokedex.common.dto.ApiError;
import org.acme.shakesperean_pokedex.common.dto.PokedexResult;
import org.acme.shakesperean_pokedex.exception.TranslationException;
import org.acme.shakesperean_pokedex.service.ShakespeareanPokedexService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/pokemon")
public class ShakespeareanPokedexController {

    private final ShakespeareanPokedexService shakespeareanPokedexService;

    @Inject
    public ShakespeareanPokedexController(ShakespeareanPokedexService shakespeareanPokedexService) {
        this.shakespeareanPokedexService = shakespeareanPokedexService;
    }

    @GET
    @Path("/{pokemonName}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get a Shakespearean Pokemon description")
    @APIResponse(
            responseCode = "200", name = "Pokemon description",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PokedexResult.class)
            )
    )
    @APIResponse(
            responseCode = "502",
            name = "External api error",
            description = "returned in case of external API errors. externalCode contains the remote api HTTP status returned",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiError.class)
            )
    )
    @APIResponse(
            responseCode = "500",
            name = "Internal server error",
            description = "returned in case of an internal server error. code might provide additional details",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiError.class)
            )
    )
    public PokedexResult getDescription(@PathParam("pokemonName") String pokemonName) throws TranslationException {
        return shakespeareanPokedexService.getShakespeareanResult(pokemonName);
    }
}