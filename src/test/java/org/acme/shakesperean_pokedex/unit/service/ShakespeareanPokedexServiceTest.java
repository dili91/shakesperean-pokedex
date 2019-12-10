package org.acme.shakesperean_pokedex.unit.service;

import com.sun.tools.javac.util.List;
import org.acme.shakesperean_pokedex.dto.PokedexResult;
import org.acme.shakesperean_pokedex.dto.fun_translations.Contents;
import org.acme.shakesperean_pokedex.dto.fun_translations.Translation;
import org.acme.shakesperean_pokedex.dto.poke_api.FlavorTextEntries;
import org.acme.shakesperean_pokedex.dto.poke_api.Language;
import org.acme.shakesperean_pokedex.dto.poke_api.PokemonSpecies;
import org.acme.shakesperean_pokedex.dto.poke_api.Version;
import org.acme.shakesperean_pokedex.service.ShakespeareanPokedexService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Unit test for ShakespeareanPokedex Service ")
class ShakespeareanPokedexServiceTest {

    private static final String A_POKEMON_NAME = "a-pokemon-name";
    private static final String A_LANGUAGE = "en";
    private static final String A_POKEMON_DESCRIPTION = "a-pokemon-description";
    private static final String A_VERSION = "alpha-sapphire";
    private static final String A_POKEMON_TRANSLATED_DESCRIPTION = "a-translated-description";
    //system under test
    private ShakespeareanPokedexService shakespeareanPokedexService;

    @BeforeEach
    public void setup() {
        shakespeareanPokedexService = new ShakespeareanPokedexService(
                this::stubPokemonSpecies,
                this::stubTranslation
        );
    }

    @Test
    @DisplayName("should return a Shakespearean Pokedex result given a Pokemon name")
    public void shouldReturnAShakespearanTranlation() {
        //when
        PokedexResult result = shakespeareanPokedexService.getShakespeareanResult("a-pokemon-name");

        //then
        assertEquals(A_POKEMON_NAME, result.getName());
        assertEquals(A_POKEMON_TRANSLATED_DESCRIPTION, result.getDescription());
    }

    private PokemonSpecies stubPokemonSpecies(String pokemonName) {
        PokemonSpecies pokemonSpecies = new PokemonSpecies();
        pokemonSpecies.setName(A_POKEMON_NAME);

        FlavorTextEntries flavorTextEntries = new FlavorTextEntries();
        Language language = new Language();
        language.setName(A_LANGUAGE);
        flavorTextEntries.setLanguage(language);
        flavorTextEntries.setFlavorText(A_POKEMON_DESCRIPTION);

        Version version = new Version();
        version.setName(A_VERSION);
        flavorTextEntries.setVersion(version);
        pokemonSpecies.setFlavorTextEntries(List.of(flavorTextEntries));

        return pokemonSpecies;
    }

    private Translation stubTranslation(String originalText) {
        Translation translation = new Translation();
        Contents contents = new Contents();
        contents.setText(originalText);
        contents.setTranslated(A_POKEMON_TRANSLATED_DESCRIPTION);
        translation.setContents(contents);
        return translation;
    }
}