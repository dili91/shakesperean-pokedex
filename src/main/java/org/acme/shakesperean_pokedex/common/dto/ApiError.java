package org.acme.shakesperean_pokedex.common.dto;

import java.util.Optional;

//todo tests
public class ApiError {

    private final String code;
    private final Optional<String> externalCode;
    private final Optional<String> message;

    public ApiError(String code, Optional<String> externalCode, Optional<String> message) {
        this.code = code;
        this.externalCode = externalCode;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public Optional<String> getExternalCode() {
        return externalCode;
    }

    public Optional<String> getMessage() {
        return message;
    }


    public static final class Builder {
        private String code;
        private Optional<String> externalCode;
        private Optional<String> message;

        private Builder() {
        }

        public static Builder anApiError() {
            return new Builder();
        }

        public Builder withCode(String code) {
            this.code = code;
            return this;
        }

        public Builder withExternalCode(String externalCode) {
            this.externalCode = Optional.of(externalCode);
            return this;
        }

        public Builder withMessage(String message) {
            this.message = Optional.of(message);
            return this;
        }

        public ApiError build() {
            return new ApiError(code, externalCode, message);
        }
    }
}
