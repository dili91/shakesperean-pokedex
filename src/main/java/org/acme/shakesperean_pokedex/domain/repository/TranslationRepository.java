package org.acme.shakesperean_pokedex.domain.repository;

import java.util.Optional;

public interface TranslationRepository {

    /**
     * Given a text gives back its translation
     * @param text the text to translate
     * @return the tranlsated text, if available
     */
    Optional<String> get(String text);
}