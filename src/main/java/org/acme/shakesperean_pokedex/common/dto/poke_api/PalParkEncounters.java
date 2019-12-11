/* Copyright 2019 freecodeformat.com */
package org.acme.shakesperean_pokedex.common.dto.poke_api;

import com.fasterxml.jackson.annotation.JsonProperty;

/* Time: 2019-12-08 16:59:3 @author freecodeformat.com @website http://www.freecodeformat.com/json2javabean.php */
public class PalParkEncounters {

    private Area area;
    @JsonProperty("base_score")
    private int baseScore;
    private int rate;

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public int getBaseScore() {
        return baseScore;
    }

    public void setBaseScore(int baseScore) {
        this.baseScore = baseScore;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

}