package org.acme.shakesperean_pokedex.integration;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.notFound;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.acme.shakesperean_pokedex.common.ErrorCode.EXTERNAL;
import static org.acme.shakesperean_pokedex.common.ErrorCode.GENERIC;
import static org.acme.shakesperean_pokedex.common.ErrorCode.TRANSLATION;
import static org.acme.shakesperean_pokedex.util.Configuration.POKEAPI_SPECIES_CHARIZARD_JSON_STUB_FILE;
import static org.acme.shakesperean_pokedex.util.Configuration.POKEAPI_SPECIES_CHARIZARD_MISSING_DESCRIPTION_JSON_STUB_FILE;
import static org.acme.shakesperean_pokedex.util.Configuration.POKE_API_SPECIES_PATH;
import static org.acme.shakesperean_pokedex.util.Configuration.TRANSLATION_API_CHARIZARD_JSON_STUB_FILE;
import static org.acme.shakesperean_pokedex.util.Configuration.TRANSLATION_API_PATH;
import static org.acme.shakesperean_pokedex.util.Util.getFunTranslationsJsonStubLocation;
import static org.acme.shakesperean_pokedex.util.Util.getPokeApiJsonStubLocation;
import static org.eclipse.jetty.http.HttpStatus.BAD_GATEWAY_502;
import static org.eclipse.jetty.http.HttpStatus.INTERNAL_SERVER_ERROR_500;
import static org.eclipse.jetty.http.HttpStatus.OK_200;
import static org.hamcrest.Matchers.equalTo;

import org.acme.shakesperean_pokedex.common.dto.PokedexResult;
import org.acme.shakesperean_pokedex.util.RemoteApiTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@DisplayName("Generic integration test")
public class IntegrationTest extends RemoteApiTest {

        private static final String A_POKEMON_NAME = "charizard";
        private static final String A_POKEMON_DESCRIPTION = "Charizard flies 'round the sky in search of powerful opponents. 't breathes fire of such most wondrous heat yond 't melts aught. However,  't nev'r turns its fiery breath on any opponent weaker than itself.";
        private static final PokedexResult A_POKEDEX_RESULT = PokedexResult.Builder.aPokedexResult()
                        .withName(A_POKEMON_NAME).withDescription(A_POKEMON_DESCRIPTION).build();

        @Test
        @DisplayName("should get 200 and a Shakespearean result in JSON format")
        public void shouldGetASuccessfulTranslation() {
                stubSuccessfulDataSet();

                given().pathParam("name", A_POKEMON_NAME).when().get("/pokemon/{name}").then().statusCode(OK_200)
                                .body("name", equalTo(A_POKEDEX_RESULT.getName()))
                                .body("description", equalTo(A_POKEDEX_RESULT.getDescription()));
        }

        @Test
        @DisplayName("should get 502 and an external api error")
        public void shouldGetAnExternalApiError() {
                stubExternalApiNotFoundError();

                given().pathParam("name", A_POKEMON_NAME).when().get("/pokemon/{name}").then()
                                .statusCode(BAD_GATEWAY_502).body("code", equalTo(EXTERNAL.value()));
        }

        @Test
        @DisplayName("should get 500 and a translation error in JSON format")
        public void shouldGetATranslationError() {
                stubIncompleteDataSet();

                given().pathParam("name", A_POKEMON_NAME).when().get("/pokemon/{name}").then()
                                .statusCode(INTERNAL_SERVER_ERROR_500).body("code", equalTo(TRANSLATION.value()));
        }

        @Test
        @DisplayName("should get 500 and a generic error in JSON format")
        public void shouldGetAGenericError() {
                stubBrokenDataSet();

                given().pathParam("name", A_POKEMON_NAME).when().get("/pokemon/{name}").then()
                                .statusCode(INTERNAL_SERVER_ERROR_500).body("code", equalTo(GENERIC.value()));
        }

        private void stubSuccessfulDataSet() {
                mockServer.stubFor(get(urlPathMatching(POKE_API_SPECIES_PATH))
                                .willReturn(ok().withHeader(CONTENT_TYPE, APPLICATION_JSON).withBodyFile(
                                                getPokeApiJsonStubLocation(POKEAPI_SPECIES_CHARIZARD_JSON_STUB_FILE))));
                mockServer.stubFor(post(urlPathEqualTo(TRANSLATION_API_PATH))
                                .willReturn(ok().withHeader(CONTENT_TYPE, APPLICATION_JSON)
                                                .withBodyFile(getFunTranslationsJsonStubLocation(
                                                                TRANSLATION_API_CHARIZARD_JSON_STUB_FILE))));
        }

        private void stubIncompleteDataSet() {
                mockServer.stubFor(get(urlPathMatching(POKE_API_SPECIES_PATH)).willReturn(
                                ok().withHeader(CONTENT_TYPE, APPLICATION_JSON).withBodyFile(getPokeApiJsonStubLocation(
                                                POKEAPI_SPECIES_CHARIZARD_MISSING_DESCRIPTION_JSON_STUB_FILE))));
        }

        private void stubBrokenDataSet() {
                mockServer.stubFor(get(urlPathMatching(POKE_API_SPECIES_PATH)).willReturn(ok()));
        }

        private void stubExternalApiNotFoundError() {
                mockServer.stubFor(get(urlPathMatching(POKE_API_SPECIES_PATH))
                                .willReturn(notFound().withBody("Not Found")));
        }

}