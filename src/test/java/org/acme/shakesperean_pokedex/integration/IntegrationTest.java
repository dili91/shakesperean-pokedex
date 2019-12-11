package org.acme.shakesperean_pokedex.integration;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.shakesperean_pokedex.common.dto.PokedexResult;
import org.acme.shakesperean_pokedex.util.RemoteApiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.acme.shakesperean_pokedex.common.TranslationError.DESCRIPTION_NOT_FOUND;
import static org.acme.shakesperean_pokedex.util.Configuration.*;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class IntegrationTest extends RemoteApiTest {

    private static final String A_POKEMON_NAME = "charizard";
    private static final String A_POKEMON_DESCRIPTION = "Charizard flies 'round the sky in search of powerful opponents. 't breathes fire of such most wondrous heat yond 't melts aught. However,  't nev'r turns its fiery breath on any opponent weaker than itself.";
    private static final PokedexResult A_POKEDEX_RESULT = PokedexResult.Builder.aPokedexResult()
            .withName(A_POKEMON_NAME)
            .withDescription(A_POKEMON_DESCRIPTION)
            .build();

    @BeforeEach
    public void reset() {
        mockServer.resetAll();
    }

    @Test
    @DisplayName("should get 200 and a Shakespearean result in JSON format")
    public void shouldGetASuccessfulTranslation() {
        stubSuccessfulResponses();

        given()
                .pathParam("name", A_POKEMON_NAME)
                .when().get("/pokemon/{name}")
                .then()
                .statusCode(200)
                .body("name", equalTo(A_POKEDEX_RESULT.getName()))
                .body("description", equalTo(A_POKEDEX_RESULT.getDescription()));
    }

    @Test
    @DisplayName("should get 502 with details in case of external api error")
    public void shouldGetAnExternalApiError() {
        stubExternalApiNotFoundError();

        given()
                .pathParam("name", A_POKEMON_NAME)
                .when().get("/pokemon/{name}")
                .then()
                .statusCode(502)
                .body("code", equalTo("external")) //todo
                .body("message", equalTo(DESCRIPTION_NOT_FOUND.value()));
    }

    @Test
    @DisplayName("should get 500 and a translation error in JSON format")
    public void shouldGetATranslationError() {
        stubDataWithMissingDescription();

        given()
                .pathParam("name", A_POKEMON_NAME)
                .when().get("/pokemon/{name}")
                .then()
                .statusCode(500)
                .body("code", equalTo("todo")) //todo
                .body("message", equalTo(DESCRIPTION_NOT_FOUND.value()));
    }

    private void stubSuccessfulResponses() {
        mockServer.stubFor(get(urlPathMatching(POKE_API_SPECIES_PATH))
                .willReturn(ok()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .withBodyFile(getPokeApiJsonStub(POKEAPI_SPECIES_CHARIZARD_JSON_STUB_FILE))
                )
        );
        mockServer.stubFor(post(urlPathEqualTo(TRANSLATION_API_PATH))
                .willReturn(ok()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .withBodyFile(getFunTranslationsJsonStub(TRANSLATION_API_CHARIZARD_JSON_STUB_FILE))
                )
        );
    }

    private void stubDataWithMissingDescription() {
        mockServer.stubFor(get(urlPathMatching(POKE_API_SPECIES_PATH))
                .willReturn(ok()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .withBodyFile(getPokeApiJsonStub(POKEAPI_SPECIES_CHARIZARD_MISSING_DESCRIPTION_JSON_STUB_FILE))
                )
        );
    }

    private void stubExternalApiNotFoundError() {
        mockServer.stubFor(get(urlPathMatching(POKE_API_SPECIES_PATH))
                .willReturn(notFound()
                        .withBody("Not Found")
                )
        );
    }

    private String getPokeApiJsonStub(String fileName) {
        return POKE_API_JSON_STUBS_FOLDER + "/" + fileName;
    }

    private String getFunTranslationsJsonStub(String fileName) {
        return FUN_TRANSLATIONS_API_JSON_STUBS_FOLDER + "/" + fileName;
    }
}