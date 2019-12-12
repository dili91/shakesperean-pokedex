package org.acme.shakesperean_pokedex.service;

import org.acme.shakesperean_pokedex.common.dto.PokedexResult;
import org.acme.shakesperean_pokedex.common.dto.PokedexResult.Builder;
import org.acme.shakesperean_pokedex.common.dto.poke_api.PokemonSpecies;
import org.acme.shakesperean_pokedex.exception.RemoteApiException;
import org.acme.shakesperean_pokedex.exception.TranslationException;
import org.acme.shakesperean_pokedex.service.connector.FunTranslationsApiClient;
import org.acme.shakesperean_pokedex.service.connector.PokeApiClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotEmpty;
import javax.ws.rs.WebApplicationException;
import java.util.function.Supplier;

/**
 * Main service class. Given an existing Pokemon name, it returns its Shakespearean translation
 */
@ApplicationScoped
public class ShakespeareanPokedexService {

    private static final String A_DEFAULT_LANGUAGE = "en";
    private static final String A_DEFAULT_VERSION = "alpha-sapphire";

    private static final String DESCRIPTION_NOT_FOUND_ERR_MSG = "Description not found for the selected Pokemon";
    private static final String TRANSLATION_NOT_AVAILABLE_ERR_MSG = "Translation not available for the selected Pokemon";

    private final PokeApiClient pokeApiClient;
    private final FunTranslationsApiClient funTranslationsApiClient;

    @Inject
    public ShakespeareanPokedexService(
            @RestClient PokeApiClient pokeApiClient,
            @RestClient FunTranslationsApiClient funTranslationsApiClient) {
        this.pokeApiClient = pokeApiClient;
        this.funTranslationsApiClient = funTranslationsApiClient;
    }

    private static boolean nullOrEmpty(String text) {
        return text == null || text.isEmpty();
    }

    /**
     * Returns a Shakespearean-like Pokedex result for the given pokemon
     *
     * @param pokemonName the name of the Pokemon to look for
     * @return an object that consists of a name and description
     * @throws TranslationException a translation exception
     */
    public PokedexResult getShakespeareanResult(@NotEmpty String pokemonName) throws TranslationException {

        PokemonSpecies species = remoteApiCallWrapper(() -> pokeApiClient.getPokemonSpecies(pokemonName));

        String originalDescription = species.getFlavorTextEntries().stream()
                .filter(entry -> entry.getLanguage().getName().equalsIgnoreCase(A_DEFAULT_LANGUAGE))
                .filter(entry -> entry.getVersion().getName().equalsIgnoreCase(A_DEFAULT_VERSION))
                .findFirst()
                .orElseThrow(() -> new TranslationException(DESCRIPTION_NOT_FOUND_ERR_MSG))
                .getFlavorText();

        if (nullOrEmpty(originalDescription)) {
            throw new TranslationException(DESCRIPTION_NOT_FOUND_ERR_MSG);
        }

        String translatedDescription = funTranslationsApiClient.translate(originalDescription)
                .getContents()
                .getTranslated();

        if (nullOrEmpty(translatedDescription)) {
            throw new TranslationException(TRANSLATION_NOT_AVAILABLE_ERR_MSG);
        }

        return Builder.aPokedexResult()
                .withName(pokemonName)
                .withDescription(translatedDescription)
                .build();
    }

    /**
     * Temporary wrapper around call to external clients to wrap exceptions of type WebApplicationException.
     * In case of underlying WebApplicationException, exceptions are wrapped into a RemoteApiException instance
     *
     * @param remoteCall the supplier containing the remote call to execute
     * @param <T>        the type of parameter returned by the remote call
     * @return the result of the remote call
     * @see "https://github.com/quarkusio/quarkus/issues/4031"
     */
    private <T> T remoteApiCallWrapper(Supplier<T> remoteCall) {
        try {
            return remoteCall.get();
        } catch (WebApplicationException wae) {
            throw new RemoteApiException(wae);
        }
    }
}
