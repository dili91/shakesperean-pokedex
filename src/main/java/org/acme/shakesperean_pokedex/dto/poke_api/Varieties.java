/* Copyright 2019 freecodeformat.com */
package org.acme.shakesperean_pokedex.dto.poke_api;

import com.fasterxml.jackson.annotation.JsonProperty;

/* Time: 2019-12-08 16:59:3 @author freecodeformat.com @website http://www.freecodeformat.com/json2javabean.php */
public class Varieties {

    @JsonProperty("is_default")
    private boolean isDefault;
    private Pokemon pokemon;

    public boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

}