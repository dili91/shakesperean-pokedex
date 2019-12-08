package org.acme.shakesperean_pokedex.service.connector;

import org.acme.shakesperean_pokedex.dto.fun_translations.Translation;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.FormParam;

import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RegisterRestClient
public interface FunTranslationsApiClient {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    Translation translate(@FormParam("text") String text);
}
