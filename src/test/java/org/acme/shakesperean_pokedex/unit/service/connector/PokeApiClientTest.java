package org.acme.shakesperean_pokedex.unit.service.connector;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.acme.shakesperean_pokedex.dto.poke_api.PokemonSpecies;
import org.acme.shakesperean_pokedex.extension.MockApiExtension;
import org.acme.shakesperean_pokedex.service.connector.PokeApiClient;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.WebApplicationException;
import java.net.URI;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Unit test fot PokeApi API connector")
@ExtendWith(MockApiExtension.class)
public class PokeApiClientTest {

    //dummies
    private static final String A_POKEMON_NAME = "charizard";
    private static final String A_POKEMON_COLOR = "red";
    private static final String A_POKEMON_DESCRIPTION = "Charizard flies around the sky in search of powerful opponents.\nIt breathes fire of such great heat that it melts anything.\nHowever, it never turns its fiery breath on any opponent\nweaker than itself.";
    private static final String A_DEFAULT_LANGUAGE = "en";
    private static final String A_DEFAULT_VERSION = "alpha-sapphire";
    private static final String A_NON_EXISTING_POKEMON_NAME = "a-non-existing-pokemon-name";

    private static final String POKEMON_SPECIES_API_PATH = "/api/v2/pokemon-species/";
    private static final String A_POKE_API_JSON_RESPONSE = "{\n" +
            "   \"name\":\"charizard\",\n" +
            "   \"color\":{\n" +
            "      \"name\":\"red\",\n" +
            "      \"url\":\"https://pokeapi.co/api/v2/pokemon-color/8/\"\n" +
            "   },\n" +
            "   \"flavor_text_entries\":[\n" +
            "      {\n" +
            "         \"flavor_text\":\"Charizard flies around the sky in search of powerful opponents.\\nIt breathes fire of such great heat that it melts anything.\\nHowever, it never turns its fiery breath on any opponent\\nweaker than itself.\",\n" +
            "         \"language\":{\n" +
            "            \"name\":\"en\",\n" +
            "            \"url\":\"https://pokeapi.co/api/v2/language/9/\"\n" +
            "         },\n" +
            "         \"version\":{\n" +
            "            \"name\":\"alpha-sapphire\",\n" +
            "            \"url\":\"https://pokeapi.co/api/v2/version/26/\"\n" +
            "         }\n" +
            "      }\n" +
            "   ]\n" +
            "}";

    //system under test
    private static PokeApiClient testClient;

    @BeforeAll
    static void setup(WireMockServer wireMockServer) {
        testClient = RestClientBuilder.newBuilder()
                .baseUri(URI.create(wireMockServer.baseUrl()))
                .build(PokeApiClient.class);
    }

    @Test
    @DisplayName("Should get a pokemon species")
    public void shouldGetAPokemonDescription(WireMockServer wireMockServer) {
        //given
        wireMockServer.stubFor(
                get(urlPathMatching(POKEMON_SPECIES_API_PATH + A_POKEMON_NAME))
                        .willReturn(okJson(A_POKE_API_JSON_RESPONSE)));

        //when
        PokemonSpecies species = testClient.getPokemonSpecies(A_POKEMON_NAME);

        //then
        verifyApiCalled(wireMockServer);
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
    public void shouldThrowAWebServiceExceptionIfPokemonSpeciesNotFound(WireMockServer wireMockServer) {
        //given
        wireMockServer.stubFor(
                get(urlPathMatching(POKEMON_SPECIES_API_PATH + A_NON_EXISTING_POKEMON_NAME))
                        .willReturn(notFound().withBody("Not Found")));

        //when
        WebApplicationException exception = assertThrows(WebApplicationException.class,
                () -> testClient.getPokemonSpecies(A_NON_EXISTING_POKEMON_NAME));

        //then
        verifyApiCalled(wireMockServer);
        assertEquals(NOT_FOUND, exception.getResponse().getStatusInfo().toEnum());
    }

    @Test
    @DisplayName("should throw an exception if api is not reachable")
    public void shouldThrowAWebServiceExceptionIfServerNotReachable(WireMockServer wireMockServer) {
        //given
        wireMockServer.stubFor(
                get(urlPathMatching(POKEMON_SPECIES_API_PATH + A_POKEMON_NAME))
                        .willReturn(serviceUnavailable()));

        //when
        WebApplicationException exception = assertThrows(WebApplicationException.class,
                () -> testClient.getPokemonSpecies(A_POKEMON_NAME));

        //then
        verifyApiCalled(wireMockServer);
        assertEquals(SERVICE_UNAVAILABLE, exception.getResponse().getStatusInfo().toEnum());
    }

    private void verifyApiCalled(WireMockServer wireMockServer) {
        wireMockServer.verify(1, getRequestedFor(urlPathMatching(POKEMON_SPECIES_API_PATH + ".*")));
    }
}