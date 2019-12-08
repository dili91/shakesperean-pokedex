package org.acme.shakesperean_pokedex.service.connector;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.shakesperean_pokedex.dto.poke_api.PokemonSpecies;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class PokeApiClientTest {

    // todo optional: make this quarkus independent
    // todo optional: stub external api

    private static final String A_POKEMON_NAME = "charizard";
    private static final String A_POKEMON_COLOR = "red";
    private static final String A_POKEMON_DESCRIPTION = "Charizard flies around the sky in search of powerful opponents.\nIt breathes fire of such great heat that it melts anything.\nHowever, it never turns its fiery breath on any opponent\nweaker than itself.";
    private static final String A_DEFAULT_LANGUAGE = "en";
    private static final String A_DEFAULT_VERSION = "alpha-sapphire";

    @Inject
    @RestClient
    PokeApiClient pokeApiClient;

    @Test
    @DisplayName("Should get a pokemon species")
    public void shouldGetAPokemonDescription() {
        PokemonSpecies species = pokeApiClient.getPokemonSpecies(A_POKEMON_NAME);

        assertEquals(A_POKEMON_NAME, species.getName());
        assertEquals(A_POKEMON_COLOR, species.getColor().getName());
        assertEquals(A_POKEMON_DESCRIPTION,
                species.getFlavorTextEntries().stream()
                        .filter(entry -> entry.getLanguage().getName().equalsIgnoreCase(A_DEFAULT_LANGUAGE))
                        .filter(entry -> entry.getVersion().getName().equalsIgnoreCase(A_DEFAULT_VERSION))
                        .findFirst()
                        .orElseThrow()
                        .getFlavorText()
        );
    }
}
