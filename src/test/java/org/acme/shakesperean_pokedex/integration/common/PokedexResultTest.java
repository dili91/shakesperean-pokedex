package org.acme.shakesperean_pokedex.integration.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import org.acme.shakesperean_pokedex.common.dto.PokedexResult;
import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.skyscreamer.jsonassert.JSONCompareMode.STRICT;

@QuarkusTest
@DisplayName("Test for PokedexResult serialization using Quarkus object mapper bean")
public class PokedexResultTest {

    public static final String A_NAME = "a-name";
    public static final String A_DESCRIPTION = "a-description";
    private static final PokedexResult A_POKEDEX_RESULT = PokedexResult.Builder.aPokedexResult()
            .withName(A_NAME)
            .withDescription(A_DESCRIPTION)
            .build();
    public static final String A_POKEDEX_RESULT_JSON = "{\"name\":\"a-name\", \"description\":\"a-description\"}";

    @Inject
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Should convert an ApiError instance to JSON")
    public void shouldDeserializeUsingBuilder() throws JsonProcessingException, JSONException {
        String json = objectMapper.writeValueAsString(A_POKEDEX_RESULT);

        assertEquals(json, A_POKEDEX_RESULT_JSON, STRICT);
    }
}
