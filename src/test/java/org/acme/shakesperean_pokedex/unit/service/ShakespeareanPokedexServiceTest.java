package org.acme.shakesperean_pokedex.unit.service;

import org.acme.shakesperean_pokedex.common.dto.PokedexResult;
import org.acme.shakesperean_pokedex.common.dto.fun_translations.Contents;
import org.acme.shakesperean_pokedex.common.dto.fun_translations.Translation;
import org.acme.shakesperean_pokedex.common.dto.poke_api.FlavorTextEntries;
import org.acme.shakesperean_pokedex.common.dto.poke_api.Language;
import org.acme.shakesperean_pokedex.common.dto.poke_api.PokemonSpecies;
import org.acme.shakesperean_pokedex.common.dto.poke_api.Version;
import org.acme.shakesperean_pokedex.exception.TranslationException;
import org.acme.shakesperean_pokedex.service.ShakespeareanPokedexService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

@DisplayName("Unit test for ShakespeareanPokedex Service ")
class ShakespeareanPokedexServiceTest {

    private static final String A_LANGUAGE = "en";
    private static final String A_VERSION = "alpha-sapphire";
    private static final String A_POKEMON_NAME = "a-pokemon-name";
    private static final String A_POKEMON_DESCRIPTION = "a-pokemon-description";
    private static final String A_POKEMON_TRANSLATED_DESCRIPTION = "a-translated-description";

    @Test
    @DisplayName("should return a Shakespearean Pokedex result given a Pokemon name")
    public void shouldReturnAShakespearanTranlation() throws TranslationException {
        //given
        ShakespeareanPokedexService shakespeareanPokedexService = new ShakespeareanPokedexService(
                name -> this.stubPositivePokeApiResult(A_POKEMON_NAME, A_POKEMON_DESCRIPTION),
                text -> this.stubPositiveTranslation(A_POKEMON_TRANSLATED_DESCRIPTION)
        );

        //when
        PokedexResult result = shakespeareanPokedexService.getShakespeareanResult(A_POKEMON_NAME);

        //then
        assertEquals(A_POKEMON_NAME, result.getName());
        assertEquals(A_POKEMON_TRANSLATED_DESCRIPTION, result.getDescription());
    }

    @Test
    @DisplayName("should return an exception if a Pokemon description is not found for the given name")
    public void shouldReturnAnExceptionIfPokemonIsNotFound() throws TranslationException {
        //given
        ShakespeareanPokedexService shakespeareanPokedexService = new ShakespeareanPokedexService(
                name -> this.stubPositivePokeApiResult(A_POKEMON_NAME, null),
                text -> this.stubPositiveTranslation(A_POKEMON_TRANSLATED_DESCRIPTION)
        );

        //expect
        assertThrows(TranslationException.class, ()
                -> shakespeareanPokedexService.getShakespeareanResult(A_POKEMON_NAME)
        );
    }

    @Test
    @DisplayName("should return an exception if a Shakespearean is not available")
    public void shouldReturnAnExceptionIfTranslationNotAvailable() throws TranslationException {
        //given
        ShakespeareanPokedexService shakespeareanPokedexService = new ShakespeareanPokedexService(
                name -> this.stubPositivePokeApiResult(A_POKEMON_NAME, A_POKEMON_DESCRIPTION),
                text -> this.stubPositiveTranslation("")
        );

        //expect
        assertThrows(TranslationException.class, ()
                -> shakespeareanPokedexService.getShakespeareanResult(A_POKEMON_NAME)
        );
    }

    private PokemonSpecies stubPositivePokeApiResult(String name, String description) {
        Version version = new Version();
        version.setName(A_VERSION);

        Language language = new Language();
        language.setName(A_LANGUAGE);

        PokemonSpecies pokemonSpecies = new PokemonSpecies();
        pokemonSpecies.setName(name);

        FlavorTextEntries flavorTextEntries = new FlavorTextEntries();
        flavorTextEntries.setLanguage(language);
        flavorTextEntries.setFlavorText(description);
        flavorTextEntries.setVersion(version);
        pokemonSpecies.setFlavorTextEntries(List.of(flavorTextEntries));

        return pokemonSpecies;
    }

    private Translation stubPositiveTranslation(String translatedText) {
        Translation translation = new Translation();
        Contents contents = new Contents();
        contents.setTranslated(translatedText);
        translation.setContents(contents);
        return translation;
    }
}