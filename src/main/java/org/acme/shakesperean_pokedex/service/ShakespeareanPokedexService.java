package org.acme.shakesperean_pokedex.service;

import org.acme.shakesperean_pokedex.dto.PokedexResult;
import org.acme.shakesperean_pokedex.dto.PokedexResult.Builder;
import org.acme.shakesperean_pokedex.service.connector.FunTranslationsApiClient;
import org.acme.shakesperean_pokedex.service.connector.PokeApiClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ShakespeareanPokedexService {

    private final PokeApiClient pokeApiClient;
    private final FunTranslationsApiClient funTranslationsApiClient;

    public ShakespeareanPokedexService(
            @RestClient PokeApiClient pokeApiClient,
            @RestClient FunTranslationsApiClient funTranslationsApiClient) {
        this.pokeApiClient = pokeApiClient;
        this.funTranslationsApiClient = funTranslationsApiClient;
    }

    public PokedexResult getDescription(String pokemonName) {
        //todo real impl

        return Builder.aPokedexResult()
                .withName(pokemonName)
                .withDescription("a-desc")
                .build();
    }
}
