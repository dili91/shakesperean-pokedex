package org.acme.shakesperean_pokedex.controller.mapper;

import org.acme.shakesperean_pokedex.common.dto.ApiError;
import org.acme.shakesperean_pokedex.common.dto.ApiError.Builder;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.status;

/**
 * Mapper that intercepts exceptions of type GenericExceptionMapper and
 * transform then in an instance of ApiError to be later serialized in JSON format
 */
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception> {

    public static final String ERR_CODE = "E:GENERIC";

    @Override
    public Response toResponse(Exception exception) {

        ApiError apiError = Builder.anApiError()
                .withCode(ERR_CODE)
                .withMessage(exception.getMessage())
                .build();

        return status(INTERNAL_SERVER_ERROR).entity(apiError).build();
    }
}