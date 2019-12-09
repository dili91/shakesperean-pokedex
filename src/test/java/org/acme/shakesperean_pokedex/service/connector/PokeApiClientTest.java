package org.acme.shakesperean_pokedex.service.connector;

import org.acme.shakesperean_pokedex.dto.poke_api.PokemonSpecies;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.WebApplicationException;
import java.net.URI;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Test fot PokeApi API connector")
public class PokeApiClientTest {

    // todo optional: stub external api

    private static final String POKE_API_ENDPOINT = "https://pokeapi.co";
    private static final String A_POKEMON_NAME = "charizard";
    private static final String A_POKEMON_COLOR = "red";
    private static final String A_POKEMON_DESCRIPTION = "Charizard flies around the sky in search of powerful opponents.\nIt breathes fire of such great heat that it melts anything.\nHowever, it never turns its fiery breath on any opponent\nweaker than itself.";
    private static final String A_DEFAULT_LANGUAGE = "en";
    private static final String A_DEFAULT_VERSION = "alpha-sapphire";

    private static PokeApiClient testClient;

    @BeforeAll
    static void setup() {
        testClient = RestClientBuilder.newBuilder()
                .baseUri(URI.create(POKE_API_ENDPOINT))
                .build(PokeApiClient.class);
    }

    @Test
    @DisplayName("Should get a pokemon species")
    public void shouldGetAPokemonDescription() {
        PokemonSpecies species = testClient.getPokemonSpecies(A_POKEMON_NAME);

        assertEquals(A_POKEMON_NAME, species.getName());
        assertEquals(A_POKEMON_COLOR, species.getColor().getName());
        assertEquals(A_POKEMON_DESCRIPTION,
                species.getFlavorTextEntries().stream()
                        .filter(entry -> entry.getLanguage().getName().equalsIgnoreCase(A_DEFAULT_LANGUAGE))
                        .filter(entry -> entry.getVersion().getName().equalsIgnoreCase(A_DEFAULT_VERSION))
                        .findFirst()
                        .get()
                        .getFlavorText()
        );
    }

    @Test
    @DisplayName("Should get an exception if pokemon species is not found")
    public void shouldGetAWebServiceException() {
        WebApplicationException exception = assertThrows(WebApplicationException.class,
                () -> testClient.getPokemonSpecies("a-non-existing-pokemon-name"));

        assertEquals(NOT_FOUND, exception.getResponse().getStatusInfo().toEnum());
    }
}
