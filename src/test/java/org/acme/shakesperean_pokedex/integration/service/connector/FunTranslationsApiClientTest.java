package org.acme.shakesperean_pokedex.integration.service.connector;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.acme.shakesperean_pokedex.dto.fun_translations.Translation;
import org.acme.shakesperean_pokedex.extension.MockApiExtension;
import org.acme.shakesperean_pokedex.service.connector.FunTranslationsApiClient;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.WebApplicationException;
import java.net.URI;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("IntegrationTest")
@DisplayName("Unit test fot FunTranslations API connector")
@ExtendWith(MockApiExtension.class)
class FunTranslationsApiClientTest {

    //dummies
    private static final String A_TEXT_IN_MODERN_ENGLISH_LANGUAGE = "today sun is shining";
    private static final String A_TEXT_IN_SHAKESPEAREAN_LANGUAGE = "The present day travelling lamp is shining";
    private static final String A_FUN_TRANSLATION_API_JSON_RESPONSE = "{\n" +
            "    \"success\": {\n" +
            "        \"total\": 1\n" +
            "    },\n" +
            "    \"contents\": {\n" +
            "        \"translated\": \"The present day travelling lamp is shining\",\n" +
            "        \"text\": \"today sun is shining\",\n" +
            "        \"translation\": \"shakespeare\"\n" +
            "    }\n" +
            "}";

    //system under test
    private static FunTranslationsApiClient testClient;

    @BeforeAll
    static void setup(WireMockServer wireMockServer) {
        testClient = RestClientBuilder.newBuilder()
                .baseUri(URI.create(wireMockServer.baseUrl()))
                .build(FunTranslationsApiClient.class);
    }

    @Test
    @DisplayName("should get a text translated in Shakespeare's language")
    public void shouldGetAShakespeareanTranslation(WireMockServer wireMockServer) {
        //given
        wireMockServer.stubFor(
                post(anyUrl()).withRequestBody(containing("text"))
                        .willReturn(okJson(A_FUN_TRANSLATION_API_JSON_RESPONSE)));

        //when
        Translation translation = testClient.translate(A_TEXT_IN_MODERN_ENGLISH_LANGUAGE);

        //then
        wireMockServer.verify(1, postRequestedFor(anyUrl()));
        assertEquals(A_TEXT_IN_MODERN_ENGLISH_LANGUAGE, translation.getContents().getText());
        assertEquals(A_TEXT_IN_SHAKESPEAREAN_LANGUAGE, translation.getContents().getTranslated());
    }

    @Test
    @DisplayName("should throw an exception if api is not reachable")
    public void shouldThrowAWebServiceExceptionIfServerNotReachable(WireMockServer wireMockServer) {
        //given
        wireMockServer.stubFor(
                post(anyUrl())
                        .willReturn(serviceUnavailable()));

        //when
        WebApplicationException exception = assertThrows(WebApplicationException.class,
                () -> testClient.translate(null));

        //then
        wireMockServer.verify(1, postRequestedFor(anyUrl()));
        assertEquals(SERVICE_UNAVAILABLE, exception.getResponse().getStatusInfo().toEnum());
    }
}