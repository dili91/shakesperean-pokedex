package org.acme.shakesperean_pokedex.integration;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.shakesperean_pokedex.dto.PokedexResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
@Tag("IntegrationTest")
public class IntegrationTest {

    private static final String A_POKEMON_NAME = "charizard";
    private static final PokedexResult A_POKEDEX_RESULT = PokedexResult.Builder.aPokedexResult()
            .withName(A_POKEMON_NAME)
            .build();

    @Test
    @DisplayName("should return a Shakespearean Pokedex result")
    public void testHelloEndpoint() {
        given()
                .queryParam("name", A_POKEMON_NAME)
                .when().get("/poke")
                .then()
                .statusCode(200)
                .body("name", equalTo(A_POKEDEX_RESULT.getName()));
    }

}