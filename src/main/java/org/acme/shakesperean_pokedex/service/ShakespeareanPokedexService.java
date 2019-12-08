package org.acme.shakesperean_pokedex.service;

import org.acme.shakesperean_pokedex.dto.PokedexResult;
import org.acme.shakesperean_pokedex.dto.PokedexResult.Builder;
import org.acme.shakesperean_pokedex.service.connector.FunTranslationsApiClient;
import org.acme.shakesperean_pokedex.service.connector.PokeApiClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ShakespeareanPokedexService {

    @Inject
    @RestClient
    PokeApiClient pokeApiClient;

    @Inject
    @RestClient
    FunTranslationsApiClient funTranslationsApiClient;

    public PokedexResult getDescription(String pokemonName) {
        //todo real impl

        return Builder.aPokedexResult()
                .withName(pokemonName)
                .withDescription("a-desc")
                .build();
    }
}
