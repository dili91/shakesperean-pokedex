/* Copyright 2019 freecodeformat.com */
package org.acme.shakesperean_pokedex.common.dto.poke_api;

import com.fasterxml.jackson.annotation.JsonProperty;

/* Time: 2019-12-08 16:59:3 @author freecodeformat.com @website http://www.freecodeformat.com/json2javabean.php */
public class FlavorTextEntries {

    @JsonProperty("flavor_text")
    private String flavorText;
    private Language language;
    private Version version;

    public String getFlavorText() {
        return flavorText;
    }

    public void setFlavorText(String flavorText) {
        this.flavorText = flavorText;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

}