package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class IntegrationTest {

    //todo

    @Test
    @DisplayName("should return a hello message")
    public void testHelloEndpoint() {
        given()
          .when().get("/poke")
          .then()
             .statusCode(200)
             .body(is("whatever"));
    }

}