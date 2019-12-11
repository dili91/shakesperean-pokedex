package org.acme.shakesperean_pokedex.unit.controller.mapper;

import org.acme.shakesperean_pokedex.common.dto.ApiError;
import org.acme.shakesperean_pokedex.controller.mapper.RemoteApiExceptionMapper;
import org.acme.shakesperean_pokedex.exception.RemoteApiException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static org.acme.shakesperean_pokedex.common.ErrorCode.EXTERNAL;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RemoteApiExceptionMapperTest {

    //dummies
    private static final RemoteApiException A_REMOTE_API_EXCEPTION = new RemoteApiException(new WebApplicationException());

    //sut
    private RemoteApiExceptionMapper remoteApiExceptionMapper = new RemoteApiExceptionMapper();

    @Test
    @DisplayName("should build a generic error response")
    public void shouldBuildAnErrorResponse() {
        //when
        Response response = remoteApiExceptionMapper.toResponse(A_REMOTE_API_EXCEPTION);

        ApiError apiError = (ApiError) response.getEntity();
        assertEquals(EXTERNAL.value(), apiError.getCode());
        assertEquals(INTERNAL_SERVER_ERROR.getStatusCode(), Integer.valueOf(apiError.getExternalCode().get()));
        assertEquals(INTERNAL_SERVER_ERROR.getReasonPhrase(), apiError.getMessage().get());
    }
}
