package org.acme.shakesperean_pokedex.unit.common.dto.builder;

import org.acme.shakesperean_pokedex.common.dto.ApiError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiErrorBuilderTest {

    public static final String AN_ERROR_CODE = "a-code";
    public static final String AN_ERROR_EXTERNAL_CODE = "a-external-code";
    public static final String AN_ERROR_MESSAGE = "a-message";

    @Test
    @DisplayName("should build an api error")
    public void shouldBuildAPokedexResult() {
        //when
        ApiError apiError = ApiError.Builder.anApiError()
                .withCode(AN_ERROR_CODE)
                .withExternalCode(AN_ERROR_EXTERNAL_CODE)
                .withMessage(AN_ERROR_MESSAGE)
                .build();

        //then
        assertEquals(AN_ERROR_CODE, apiError.getCode());
        assertEquals(AN_ERROR_EXTERNAL_CODE, apiError.getExternalCode().get());
        assertEquals(AN_ERROR_MESSAGE, apiError.getMessage().get());
    }
}
