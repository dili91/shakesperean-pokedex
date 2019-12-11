package org.acme.shakesperean_pokedex.integration;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.shakesperean_pokedex.dto.PokedexResult;
import org.acme.shakesperean_pokedex.util.RemoteApiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
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
    public void setup() {
        mockServer.stubFor(get(urlPathMatching(POKE_API_SPECIES_PATH))
                .willReturn(ok()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .withBodyFile(POKEAPI_SPECIES_CHARIZARD_JSON_FILE)
                )
        );

        mockServer.stubFor(post(urlPathEqualTo(TRANSLATION_API_PATH))
                .willReturn(ok()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .withBodyFile(TRANSLATION_API_CHARIZARD_JSON_FILE)
                )
        );
    }

    @Test
    @DisplayName("should get a Shakespearean Pokedex result")
    public void shouldGetATranslation() {
        given()
                .pathParam("name", A_POKEMON_NAME)
                .when().get("/pokemon/{name}")
                .then()
                .statusCode(200)
                .body("name", equalTo(A_POKEDEX_RESULT.getName()))
                .body("description", equalTo(A_POKEDEX_RESULT.getDescription()));
    }

}