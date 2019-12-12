package org.acme.shakesperean_pokedex.controller.mapper;

import org.acme.shakesperean_pokedex.common.dto.ApiError;
import org.acme.shakesperean_pokedex.common.dto.ApiError.Builder;
import org.acme.shakesperean_pokedex.exception.TranslationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.status;

/**
 * Mapper that intercepts exceptions of type TranslationException and
 * transform then in an instance of ApiError to be later serialized in JSON format
 */
@Provider
public class TranslationExceptionMapper implements ExceptionMapper<TranslationException> {

    public static final String ERR_CODE = "E:TRANSLATION";

    @Override
    public Response toResponse(TranslationException exception) {

        ApiError apiError = Builder.anApiError()
                .withCode(ERR_CODE)
                .withMessage(exception.getMessage())
                .build();

        return status(Response.Status.INTERNAL_SERVER_ERROR).entity(apiError).build();
    }
}