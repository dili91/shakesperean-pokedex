package org.acme.shakesperean_pokedex.service.connector;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.shakesperean_pokedex.dto.fun_translations.Translation;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class FunTranslationsApiClientTest {
    // todo optional: make this quarkus independent
    // todo optional: stub external api

    private static final String A_TEXT_IN_MODERN_ENGLISH_LANGUAGE = "You gave Mr. Tim a hearty meal, but unfortunately what he ate made him die.";
    private static final String A_TEXT_IN_SHAKESPEAREAN_LANGUAGE = "Thou did giveth Mr. Tim a hearty meal,  but unfortunately what he englut did maketh him kicketh the bucket.";

    @Inject
    @RestClient
    FunTranslationsApiClient funTranslationsApiClient;

    @Test
    @DisplayName("should get a text translated in Shakespeare's language")
    public void shouldGetAShakespeareanTranslation(){
        Translation translation = funTranslationsApiClient.translate(A_TEXT_IN_MODERN_ENGLISH_LANGUAGE);

        assertEquals(A_TEXT_IN_MODERN_ENGLISH_LANGUAGE, translation.getContents().getText());
        assertEquals(A_TEXT_IN_SHAKESPEAREAN_LANGUAGE, translation.getContents().getTranslated());
    }

}