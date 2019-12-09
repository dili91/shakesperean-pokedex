package org.acme.shakesperean_pokedex.service.connector;

import org.acme.shakesperean_pokedex.dto.fun_translations.Translation;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Test fot FunTranslations API connector")
class FunTranslationsApiClientTest {
    // todo optional: stub external api

    private static final String FUN_TRANSLATIONS_API_ENDPOINT = "https://api.funtranslations.com/translate/shakespeare.json";
    private static final String A_TEXT_IN_MODERN_ENGLISH_LANGUAGE = "You gave Mr. Tim a hearty meal, but unfortunately what he ate made him die.";
    private static final String A_TEXT_IN_SHAKESPEAREAN_LANGUAGE = "Thou did giveth Mr. Tim a hearty meal,  but unfortunately what he englut did maketh him kicketh the bucket.";

    private static FunTranslationsApiClient testClient;

    @BeforeAll
    static void setup() {
        testClient = RestClientBuilder.newBuilder()
                .baseUri(URI.create(FUN_TRANSLATIONS_API_ENDPOINT))
                .build(FunTranslationsApiClient.class);
    }

    @Test
    @DisplayName("should get a text translated in Shakespeare's language")
    public void shouldGetAShakespeareanTranslation() {
        Translation translation = testClient.translate(A_TEXT_IN_MODERN_ENGLISH_LANGUAGE);

        assertEquals(A_TEXT_IN_MODERN_ENGLISH_LANGUAGE, translation.getContents().getText());
        assertEquals(A_TEXT_IN_SHAKESPEAREAN_LANGUAGE, translation.getContents().getTranslated());
    }

}