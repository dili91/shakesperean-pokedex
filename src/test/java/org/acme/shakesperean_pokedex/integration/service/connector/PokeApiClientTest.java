package org.acme.shakesperean_pokedex.integration.service.connector;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.shakesperean_pokedex.dto.poke_api.PokemonSpecies;
import org.acme.shakesperean_pokedex.service.connector.PokeApiClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Integration test fot PokeApi API connector")
@QuarkusTest
public class PokeApiClientTest {

    //dummies
    private static final String A_POKEMON_NAME = "charizard";
    private static final String A_POKEMON_COLOR = "red";
    private static final String A_POKEMON_DESCRIPTION = "Charizard flies around the sky in search of powerful opponents.\nIt breathes fire of such great heat that it melts anything.\nHowever, it never turns its fiery breath on any opponent\nweaker than itself.";
    private static final String A_DEFAULT_LANGUAGE = "en";
    private static final String A_DEFAULT_VERSION = "alpha-sapphire";
    private static final String A_NON_EXISTING_POKEMON_NAME = "a-non-existing-pokemon-name";

    //system under test
    @Inject
    @RestClient
    PokeApiClient pokeApiClient;

    @Test
    @DisplayName("Should get a pokemon species")
    public void shouldGetAPokemonDescription() {
        //when
        PokemonSpecies species = pokeApiClient.getPokemonSpecies(A_POKEMON_NAME);

        //then
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
    @DisplayName("Should throw an exception if a pokemon species is not found")
    public void shouldThrowAWebServiceExceptionIfPokemonSpeciesNotFound() {
        //when
        WebApplicationException exception = assertThrows(WebApplicationException.class,
                () -> pokeApiClient.getPokemonSpecies(A_NON_EXISTING_POKEMON_NAME));

        //then
        assertEquals(NOT_FOUND, exception.getResponse().getStatusInfo().toEnum());
    }

}
