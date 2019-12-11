package org.acme.shakesperean_pokedex.unit.controller.mapper;

import org.acme.shakesperean_pokedex.common.dto.ApiError;
import org.acme.shakesperean_pokedex.controller.mapper.TranslationExceptionMapper;
import org.acme.shakesperean_pokedex.exception.TranslationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.acme.shakesperean_pokedex.common.ErrorCode.TRANSLATION;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TranslationExceptionMapperTest {

    //dummies
    public static final String AN_ERROR_MESSAGE = "an-error-message";
    private static final TranslationException A_TRANSLATION_EXCEPTION = new TranslationException(AN_ERROR_MESSAGE);

    //sut
    private TranslationExceptionMapper translationExceptionMapper = new TranslationExceptionMapper();

    @Test
    @DisplayName("should build a generic error response")
    public void shouldBuildAnErrorResponse() {
        //when
        Response response = translationExceptionMapper.toResponse(A_TRANSLATION_EXCEPTION);

        ApiError apiError = (ApiError) response.getEntity();
        assertEquals(TRANSLATION.value(), apiError.getCode());
        assertEquals(AN_ERROR_MESSAGE, apiError.getMessage().get());
    }
}
