package org.acme.shakesperean_pokedex.integration_native;

import io.quarkus.test.junit.NativeImageTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.acme.shakesperean_pokedex.common.ErrorCode.EXTERNAL;
import static org.eclipse.jetty.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

/**
 * Execute integration tests against native image. In this mode the real environment is tested,
 * test injection and WireMock stubs are not available
 */
@NativeImageTest
public class IntegrationNativeTest {
    private static final Object A_POKEMON_NAME = "charizard";
    private static final Object A_NON_EXISTING_POKEMON_NAME = "dummy";

    @Test
    @DisplayName("should get 200 and a Shakespearean result in JSON format")
    public void shouldGetASuccessfulTranslation() {
        given()
                .pathParam("name", A_POKEMON_NAME)
                .when().get("/pokemon/{name}")
                .then()
                .statusCode(OK_200)
                .body("name", equalTo(A_POKEMON_NAME))
                .body("description", not(emptyOrNullString()));
    }

    @Test
    @DisplayName("should get an api error")
    public void shouldGetANotFoundError() {
        given()
                .pathParam("name", A_NON_EXISTING_POKEMON_NAME)
                .when().get("/pokemon/{name}")
                .then()
                .statusCode(BAD_GATEWAY_502)
                .body("code", equalTo(EXTERNAL.value()))
                .body("externalCode", equalTo(String.valueOf(NOT_FOUND_404)));
    }
}