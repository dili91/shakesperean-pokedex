package org.acme.shakesperean_pokedex.unit.controller.mapper;

import org.acme.shakesperean_pokedex.common.dto.ApiError;
import org.acme.shakesperean_pokedex.controller.mapper.GenericExceptionMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.acme.shakesperean_pokedex.common.ErrorCode.GENERIC;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenericExceptionMapperTest {

    //dummies
    public static final String AN_ERROR_MESSAGE = "an-error-message";
    private static final RuntimeException A_GENERIC_EXCEPTION = new RuntimeException(AN_ERROR_MESSAGE);

    //sut
    private GenericExceptionMapper genericExceptionMapper = new GenericExceptionMapper();

    @Test
    @DisplayName("should build a generic error response")
    public void shouldBuildAnErrorResponse() {
        //when
        Response response = genericExceptionMapper.toResponse(A_GENERIC_EXCEPTION);

        ApiError apiError = (ApiError) response.getEntity();
        assertEquals(GENERIC.value(), apiError.getCode());
        assertEquals(AN_ERROR_MESSAGE, apiError.getMessage().get());
    }
}
