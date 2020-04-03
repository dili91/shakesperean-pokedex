package org.acme.shakesperean_pokedex.exception;

/**
 * Translation checked exception class
 */
public class TranslationException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public TranslationException(String message) {
        super(message);
    }
}
