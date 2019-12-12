package org.acme.shakesperean_pokedex.common.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Optional;

@JsonDeserialize(builder = ApiError.Builder.class)
public class ApiError {

    private final String code;
    private final Optional<String> externalCode;
    private final Optional<String> message;

    public ApiError(String code, String externalCode, String message) {
        this.code = code;
        this.externalCode = Optional.ofNullable(externalCode);
        this.message = Optional.ofNullable(message);
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
        private String externalCode;
        private String message;

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
            this.externalCode = externalCode;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public ApiError build() {
            return new ApiError(code, externalCode, message);
        }
    }
}
