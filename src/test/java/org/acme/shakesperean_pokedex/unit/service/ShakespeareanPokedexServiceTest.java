package org.acme.shakesperean_pokedex.unit.service;

import org.acme.shakesperean_pokedex.dto.PokedexResult;
import org.acme.shakesperean_pokedex.dto.fun_translations.Contents;
import org.acme.shakesperean_pokedex.dto.fun_translations.Translation;
import org.acme.shakesperean_pokedex.dto.poke_api.PokemonSpecies;
import org.acme.shakesperean_pokedex.service.ShakespeareanPokedexService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Unit test for ShakespeareanPokedex Service ")
class ShakespeareanPokedexServiceTest {

    //system under test
    private static ShakespeareanPokedexService shakespeareanPokedexService;

    @BeforeAll
    public static void setup() {
        shakespeareanPokedexService = new ShakespeareanPokedexService(
                name -> {
                    PokemonSpecies pm = new PokemonSpecies();
                    pm.setName("andrea!");
                    return pm;
                },
                text -> {
                    Translation tr = new Translation();
                    Contents c = new Contents();
                    c.setTranslated("hello!");
                    tr.setContents(c);
                    return tr;
                }
        );
    }

    @Test
    @DisplayName("should return a Shakespearean Pokedex result given a Pokemon name")
    public void shouldReturnAShakespearanTranlation() {
        //when
        PokedexResult result = shakespeareanPokedexService.getDescription("a-pokemon-name");

        //then
        assertEquals("andrea!", result.getName());
        assertEquals("hello!", result.getDescription());
    }
}