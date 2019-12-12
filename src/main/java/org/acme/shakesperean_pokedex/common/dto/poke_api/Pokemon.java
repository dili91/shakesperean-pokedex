/* Copyright 2019 freecodeformat.com */
package org.acme.shakesperean_pokedex.common.dto.poke_api;

public class Pokemon {
    //todo: improve dto and all its dependencies with Optional values

    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}