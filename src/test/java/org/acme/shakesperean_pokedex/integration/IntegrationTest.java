package org.acme.shakesperean_pokedex.integration;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.shakesperean_pokedex.dto.PokedexResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class IntegrationTest {

    private static final String A_POKEMON_NAME = "charizard";
    private static final String A_POKEMON_DESCRIPTION = "Charizard flies 'round the sky in search of powerful opponents. 't breathes fire of such most wondrous heat yond 't melts aught. However,  't nev'r turns its fiery breath on any opponent weaker than itself.";
    private static final PokedexResult A_POKEDEX_RESULT = PokedexResult.Builder.aPokedexResult()
            .withName(A_POKEMON_NAME)
            .withDescription(A_POKEMON_DESCRIPTION)
            .build();

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