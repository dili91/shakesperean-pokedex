package org.acme.shakesperean_pokedex.dto;

public class PokedexResult {
    private String name;
    private String description;

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
            PokedexResult pokedexResult = new PokedexResult();
            pokedexResult.name = this.name;
            pokedexResult.description = this.description;
            return pokedexResult;
        }
    }
}
