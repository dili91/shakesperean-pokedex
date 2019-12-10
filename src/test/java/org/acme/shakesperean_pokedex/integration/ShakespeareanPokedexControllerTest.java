package org.acme.shakesperean_pokedex.integration;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.shakesperean_pokedex.dto.PokedexResult;
import org.acme.shakesperean_pokedex.util.MockApiExtension;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
//@Tag("IntegrationTest")
@Disabled("todo: enabled this again")
@ExtendWith(MockApiExtension.class)
public class ShakespeareanPokedexControllerTest {

    //todo rename it to controller

    private static final String A_POKEMON_NAME = "charizard";
    private static final PokedexResult A_POKEDEX_RESULT = PokedexResult.Builder.aPokedexResult()
            .withName(A_POKEMON_NAME)
            .build();

    //todo setup wiremock stubs for external api call

    //todo inject test api client

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