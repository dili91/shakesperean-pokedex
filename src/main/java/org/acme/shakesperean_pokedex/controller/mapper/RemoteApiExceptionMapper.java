package org.acme.shakesperean_pokedex.controller.mapper;

import org.acme.shakesperean_pokedex.common.dto.ApiError;
import org.acme.shakesperean_pokedex.common.dto.ApiError.Builder;
import org.acme.shakesperean_pokedex.exception.RemoteApiException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.status;

/**
 * Mapper that intercepts exceptions of type RemoteApiException
 * Currently required due to a Quarkus open issue
 *
 * @see "https://github.com/quarkusio/quarkus/issues/4031"
 */
@Provider
public class RemoteApiExceptionMapper implements ExceptionMapper<RemoteApiException> {

    public static final String ERR_CODE = "E:EXTERNAL";

    @Override
    public Response toResponse(RemoteApiException exception) {

        ApiError apiError = Builder.anApiError()
                .withCode(ERR_CODE)
                .withMessage(exception.getWrappedException().getResponse().getStatusInfo().getReasonPhrase())
                .withExternalCode(String.valueOf(exception.getWrappedException().getResponse().getStatusInfo().getStatusCode()))
                .build();

        return status(Response.Status.BAD_GATEWAY).entity(apiError).build();
    }
}