package org.acme.shakesperean_pokedex.service;

import org.acme.shakesperean_pokedex.dto.PokedexResult;
import org.acme.shakesperean_pokedex.dto.PokedexResult.Builder;
import org.acme.shakesperean_pokedex.dto.fun_translations.Translation;
import org.acme.shakesperean_pokedex.dto.poke_api.PokemonSpecies;
import org.acme.shakesperean_pokedex.service.connector.FunTranslationsApiClient;
import org.acme.shakesperean_pokedex.service.connector.PokeApiClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ShakespeareanPokedexService {

    private final PokeApiClient pokeApiClient;
    private final FunTranslationsApiClient funTranslationsApiClient;

    @Inject
    public ShakespeareanPokedexService(
            @RestClient PokeApiClient pokeApiClient,
            @RestClient FunTranslationsApiClient funTranslationsApiClient) {
        this.pokeApiClient = pokeApiClient;
        this.funTranslationsApiClient = funTranslationsApiClient;
    }

    public PokedexResult getDescription(String pokemonName) {
        //todo real impl

        PokemonSpecies pokemonSpecies = pokeApiClient.getPokemonSpecies(pokemonName);

        Translation translation = funTranslationsApiClient.translate(pokemonSpecies.getName());

        return Builder.aPokedexResult()
                .withName(pokemonSpecies.getName())
                .withDescription(translation.getContents().getTranslated())
                .build();
    }
}
