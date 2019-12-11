package org.acme.shakesperean_pokedex.controller.mapper;

import org.acme.shakesperean_pokedex.common.dto.ApiError;
import org.acme.shakesperean_pokedex.common.dto.ApiError.Builder;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.status;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception> {

    public static final String ERR_CODE = "E:GENERIC";

    @Override
    public Response toResponse(Exception exception) {

        ApiError apiError = Builder.anApiError()
                .withCode(ERR_CODE)
                .withMessage(exception.getMessage())
                .build();

        return status(Response.Status.INTERNAL_SERVER_ERROR).entity(apiError).build();
    }
}