package org.acme.shakesperean_pokedex.integration.service.connector;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.shakesperean_pokedex.dto.poke_api.PokemonSpecies;
import org.acme.shakesperean_pokedex.service.connector.PokeApiClient;
import org.acme.shakesperean_pokedex.util.RemoteApiTest;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;
import static org.acme.shakesperean_pokedex.util.Configuration.POKEAPI_SPECIES_CHARIZARD_JSON_FILE;
import static org.acme.shakesperean_pokedex.util.Configuration.POKE_API_SPECIES_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Integration test fot PokeApi API connector")
@QuarkusTest
public class PokeApiClientTest extends RemoteApiTest {

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
        //given
        mockServer.stubFor(get(urlPathMatching(POKE_API_SPECIES_PATH))
                .willReturn(ok()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .withBodyFile(POKEAPI_SPECIES_CHARIZARD_JSON_FILE)
                )
        );

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
        //given
        mockServer.stubFor(get(urlPathMatching(POKE_API_SPECIES_PATH))
                .willReturn(notFound().withBody("Not found")));

        //when
        WebApplicationException exception = assertThrows(WebApplicationException.class,
                () -> pokeApiClient.getPokemonSpecies(A_NON_EXISTING_POKEMON_NAME));

        //then
        assertEquals(NOT_FOUND, exception.getResponse().getStatusInfo().toEnum());
    }

    @Test
    @DisplayName("Should throw an exception if service is unavailable")
    public void shouldThrowAWebServiceExceptionIfServiceIsUnavailable() {
        //given
        mockServer.stubFor(get(anyUrl())
                .willReturn(serviceUnavailable()));

        //when
        WebApplicationException exception = assertThrows(WebApplicationException.class,
                () -> pokeApiClient.getPokemonSpecies(A_POKEMON_NAME));

        //then
        assertEquals(SERVICE_UNAVAILABLE, exception.getResponse().getStatusInfo().toEnum());
    }


}
