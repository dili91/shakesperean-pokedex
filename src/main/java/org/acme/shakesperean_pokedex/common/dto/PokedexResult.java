package org.acme.shakesperean_pokedex.common.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = PokedexResult.Builder.class)
public class PokedexResult {
    private final String name;
    private final String description;

    public PokedexResult(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static final class Builder {
        private String name;
        private String description;

        private Builder() {
        }

        public static Builder aPokedexResult() {
            return new Builder();
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public PokedexResult build() {
            return new PokedexResult(name, description);
        }
    }
}
