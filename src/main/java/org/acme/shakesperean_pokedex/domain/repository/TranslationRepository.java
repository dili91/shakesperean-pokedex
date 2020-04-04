package org.acme.shakesperean_pokedex.domain.repository;

public interface TranslationRepository {

    /**
     * Given a text gives back its translation
     * @param text the text to translate
     * @return the tranlsated text
     */
    String getTranslation(String text);
}