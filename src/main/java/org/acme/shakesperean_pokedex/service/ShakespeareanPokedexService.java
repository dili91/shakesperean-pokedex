package org.acme.shakesperean_pokedex.service;

import org.acme.shakesperean_pokedex.dto.PokedexResult;
import org.acme.shakesperean_pokedex.dto.PokedexResult.Builder;
import org.acme.shakesperean_pokedex.service.connector.FunTranslationsApiClient;
import org.acme.shakesperean_pokedex.service.connector.PokeApiClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotEmpty;

@ApplicationScoped
public class ShakespeareanPokedexService {

    //todo make these configurable
    private static final String A_DEFAULT_LANGUAGE = "en";
    private static final String A_DEFAULT_VERSION = "alpha-sapphire";

    private final PokeApiClient pokeApiClient;
    private final FunTranslationsApiClient funTranslationsApiClient;

    @Inject
    public ShakespeareanPokedexService(
            @RestClient PokeApiClient pokeApiClient,
            @RestClient FunTranslationsApiClient funTranslationsApiClient) {
        this.pokeApiClient = pokeApiClient;
        this.funTranslationsApiClient = funTranslationsApiClient;
    }

    /**
     * Returns a Shakespearean-like Pokedex result for the given pokemon
     *
     * @param pokemonName the name of the Pokemon to look for
     * @return an object that consists of a name and description
     */
    public PokedexResult getShakespeareanResult(@NotEmpty String pokemonName) {
        String originalDescription = pokeApiClient.getPokemonSpecies(pokemonName).getFlavorTextEntries().stream()
                .filter(entry -> entry.getLanguage().getName().equalsIgnoreCase(A_DEFAULT_LANGUAGE))
                .filter(entry -> entry.getVersion().getName().equalsIgnoreCase(A_DEFAULT_VERSION))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Description not found"))
                .getFlavorText();

        String translatedDescription = funTranslationsApiClient.translate(originalDescription).getContents().getTranslated();

        return Builder.aPokedexResult()
                .withName(pokemonName)
                .withDescription(translatedDescription)
                .build();
    }
}
