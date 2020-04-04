package org.acme.shakesperean_pokedex.domain;

public class ShakespereanPokedexItem {
    private String name;
    private String description;
    private String translatedDescription;

    public ShakespereanPokedexItem(String name, String description, String translatedDescription) {
        this.name = name;
        this.description = description;
        this.translatedDescription = translatedDescription;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTranslatedDescription() {
        return translatedDescription;
    }

    public static final class Builder {
        private String name;
        private String description;
        private String translatedDescription;

        private Builder() {
        }

        public static Builder aShakespereanPokedexItem() {
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

        public Builder withTranslatedDescription(String translatedDescription) {
            this.translatedDescription = translatedDescription;
            return this;
        }

        public ShakespereanPokedexItem build() {
            return new ShakespereanPokedexItem(name, description, translatedDescription);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((translatedDescription == null) ? 0 : translatedDescription.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ShakespereanPokedexItem other = (ShakespereanPokedexItem) obj;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (translatedDescription == null) {
            if (other.translatedDescription != null)
                return false;
        } else if (!translatedDescription.equals(other.translatedDescription))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ShakespereanPokedexItem [description=" + description + ", name=" + name + ", translatedDescription="
                + translatedDescription + "]";
    }

}