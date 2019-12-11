package org.acme.shakesperean_pokedex.common;

public enum TranslationError {
    DESCRIPTION_NOT_FOUND("Description not found for the selected Pokemon"),
    TRANSLATION_NOT_AVAILABLE("Translation not available for the selected Pokemon");

    private final String value;

    TranslationError(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
