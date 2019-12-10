package org.acme.shakesperean_pokedex.integration.service.connector;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.shakesperean_pokedex.dto.fun_translations.Translation;
import org.acme.shakesperean_pokedex.service.connector.FunTranslationsApiClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Integration test fot FunTranslations API connector")
@QuarkusTest
class FunTranslationsApiClientTest {

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
        //when
        Translation translation = funTranslationsApiClient.translate(A_TEXT_IN_MODERN_ENGLISH_LANGUAGE);

        //then
        assertEquals(A_TEXT_IN_MODERN_ENGLISH_LANGUAGE, translation.getContents().getText());
        assertEquals(A_TEXT_IN_SHAKESPEAREAN_LANGUAGE, translation.getContents().getTranslated());
    }
}