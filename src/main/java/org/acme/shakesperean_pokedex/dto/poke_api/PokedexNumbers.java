/* Copyright 2019 freecodeformat.com */
package org.acme.shakesperean_pokedex.dto.poke_api;

import com.fasterxml.jackson.annotation.JsonProperty;

/* Time: 2019-12-08 16:59:3 @author freecodeformat.com @website http://www.freecodeformat.com/json2javabean.php */
public class PokedexNumbers {

    @JsonProperty("entry_number")
    private int entryNumber;
    private Pokedex pokedex;

    public int getEntryNumber() {
        return entryNumber;
    }

    public void setEntryNumber(int entryNumber) {
        this.entryNumber = entryNumber;
    }

    public Pokedex getPokedex() {
        return pokedex;
    }

    public void setPokedex(Pokedex pokedex) {
        this.pokedex = pokedex;
    }

}