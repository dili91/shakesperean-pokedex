package org.acme.shakesperean_pokedex.integration.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import org.acme.shakesperean_pokedex.common.dto.ApiError;
import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.acme.shakesperean_pokedex.common.ErrorCode.GENERIC;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.skyscreamer.jsonassert.JSONCompareMode.STRICT;

@QuarkusTest
@DisplayName("Test for JSON serialization using Quarkus object mapper bean")
public class ApiErrorTest {

    public static final String AN_ERROR_MESSAGE = "a-message";
    private static String AN_API_ERROR_JSON = "{\"code\":\"E:GENERIC\",\"externalCode\":null,\"message\":\"a-message\"}";
    private final ApiError AN_API_ERROR = ApiError.Builder.anApiError()
            .withCode(GENERIC.value())
            .withMessage(AN_ERROR_MESSAGE)
            .build();

    @Inject
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Should convert an ApiError instance to JSON")
    public void shouldDeserializeUsingBuilder() throws JsonProcessingException, JSONException {

        String json = objectMapper.writeValueAsString(AN_API_ERROR);

        assertEquals(json, AN_API_ERROR_JSON, STRICT);
    }
}
