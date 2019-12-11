package org.acme.shakesperean_pokedex.integration.service.connector;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.shakesperean_pokedex.common.dto.fun_translations.Translation;
import org.acme.shakesperean_pokedex.service.connector.FunTranslationsApiClient;
import org.acme.shakesperean_pokedex.util.RemoteApiTest;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.TOO_MANY_REQUESTS;
import static org.acme.shakesperean_pokedex.util.Configuration.TRANSLATION_API_PATH;
import static org.acme.shakesperean_pokedex.util.Configuration.TRANSLATION_API_SUN_IS_SHINING_JSON_STUB_FILE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Integration test for FunTranslations API connector")
@QuarkusTest
class FunTranslationsApiClientTest extends RemoteApiTest {

    //dummies
    private static final String A_TEXT_IN_MODERN_ENGLISH_LANGUAGE = "today sun is shining";
    private static final String A_TEXT_IN_SHAKESPEAREAN_LANGUAGE = "The present day travelling lamp is shining";

    //system under test
    @Inject
    @RestClient
    FunTranslationsApiClient funTranslationsApiClient;

    @Test
    @DisplayName("should get a text translated in Shakespeare's language")
    public void shouldGetAShakespeareanTranslation() {
        //given
        mockServer.stubFor(post(urlPathEqualTo(TRANSLATION_API_PATH))
                .willReturn(ok()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .withBodyFile(TRANSLATION_API_SUN_IS_SHINING_JSON_STUB_FILE)
                )
        );

        //when
        Translation translation = funTranslationsApiClient.translate(A_TEXT_IN_MODERN_ENGLISH_LANGUAGE);

        //then
        assertEquals(A_TEXT_IN_MODERN_ENGLISH_LANGUAGE, translation.getContents().getText());
        assertEquals(A_TEXT_IN_SHAKESPEAREAN_LANGUAGE, translation.getContents().getTranslated());
    }

    @Test
    @DisplayName("should throw an exception when api rate limiting comes in")
    public void shouldGetATooManyRequestException() {
        //given
        mockServer.stubFor(post(urlPathEqualTo(TRANSLATION_API_PATH))
                .willReturn(status(429))
        );

        //expect
        WebApplicationException exception = assertThrows(WebApplicationException.class,
                () -> funTranslationsApiClient.translate(A_TEXT_IN_MODERN_ENGLISH_LANGUAGE));
        assertEquals(TOO_MANY_REQUESTS, exception.getResponse().getStatusInfo().toEnum());
    }
}