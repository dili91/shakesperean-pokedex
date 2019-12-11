package org.acme.shakesperean_pokedex.unit.dto.builder;

import org.acme.shakesperean_pokedex.common.dto.PokedexResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Pokedex result builder test")
public class PokedexResultBuilderTest {
    private static final String A_POKEMON_NAME = "a-pokemon-name";
    private static final String A_POKEMON_DESCRIPTION = "a-pokemon-desc";

    @Test
    @DisplayName("should build a pokedex result")
    public void shouldBuildAPokedexResult() {
        //when
        PokedexResult pokedexResult = PokedexResult.Builder.aPokedexResult()
                .withName(A_POKEMON_NAME)
                .withDescription(A_POKEMON_DESCRIPTION)
                .build();

        //then
        assertEquals(A_POKEMON_NAME, pokedexResult.getName());
        assertEquals(A_POKEMON_DESCRIPTION, pokedexResult.getDescription());
    }
}
