package org.acme.shakesperean_pokedex.common;

/**
 * Application error codes returned on API errors
 */
public enum ErrorCode {
    GENERIC("E:GENERIC"),
    TRANSLATION("E:TRANSLATION"),
    EXTERNAL("E:EXTERNAL");

    private String value;

    ErrorCode(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
