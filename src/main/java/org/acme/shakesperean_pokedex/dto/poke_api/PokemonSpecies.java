/* Copyright 2019 freecodeformat.com */
package org.acme.shakesperean_pokedex.dto.poke_api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/* Time: 2019-12-08 16:59:3 @author freecodeformat.com @website http://www.freecodeformat.com/json2javabean.php */
public class PokemonSpecies {

    @JsonProperty("base_happiness")
    private int baseHappiness;
    @JsonProperty("capture_rate")
    private int captureRate;
    private Color color;
    @JsonProperty("egg_groups")
    private List<EggGroups> eggGroups;
    @JsonProperty("evolution_chain")
    private EvolutionChain evolutionChain;
    @JsonProperty("evolves_from_species")
    private EvolvesFromSpecies evolvesFromSpecies;
    @JsonProperty("flavor_text_entries")
    private List<FlavorTextEntries> flavorTextEntries;
    @JsonProperty("form_descriptions")
    private List<String> formDescriptions;
    @JsonProperty("forms_switchable")
    private boolean formsSwitchable;
    @JsonProperty("gender_rate")
    private int genderRate;
    private List<Genera> genera;
    private Generation generation;
    @JsonProperty("growth_rate")
    private GrowthRate growthRate;
    private Habitat habitat;
    @JsonProperty("has_gender_differences")
    private boolean hasGenderDifferences;
    @JsonProperty("hatch_counter")
    private int hatchCounter;
    private int id;
    @JsonProperty("is_baby")
    private boolean isBaby;
    private String name;
    private List<Names> names;
    private int order;
    @JsonProperty("pal_park_encounters")
    private List<PalParkEncounters> palParkEncounters;
    @JsonProperty("pokedex_numbers")
    private List<PokedexNumbers> pokedexNumbers;
    private Shape shape;
    private List<Varieties> varieties;

    public int getBaseHappiness() {
        return baseHappiness;
    }

    public void setBaseHappiness(int baseHappiness) {
        this.baseHappiness = baseHappiness;
    }

    public int getCaptureRate() {
        return captureRate;
    }

    public void setCaptureRate(int captureRate) {
        this.captureRate = captureRate;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public List<EggGroups> getEggGroups() {
        return eggGroups;
    }

    public void setEggGroups(List<EggGroups> eggGroups) {
        this.eggGroups = eggGroups;
    }

    public EvolutionChain getEvolutionChain() {
        return evolutionChain;
    }

    public void setEvolutionChain(EvolutionChain evolutionChain) {
        this.evolutionChain = evolutionChain;
    }

    public EvolvesFromSpecies getEvolvesFromSpecies() {
        return evolvesFromSpecies;
    }

    public void setEvolvesFromSpecies(EvolvesFromSpecies evolvesFromSpecies) {
        this.evolvesFromSpecies = evolvesFromSpecies;
    }

    public List<FlavorTextEntries> getFlavorTextEntries() {
        return flavorTextEntries;
    }

    public void setFlavorTextEntries(List<FlavorTextEntries> flavorTextEntries) {
        this.flavorTextEntries = flavorTextEntries;
    }

    public List<String> getFormDescriptions() {
        return formDescriptions;
    }

    public void setFormDescriptions(List<String> formDescriptions) {
        this.formDescriptions = formDescriptions;
    }

    public boolean getFormsSwitchable() {
        return formsSwitchable;
    }

    public void setFormsSwitchable(boolean formsSwitchable) {
        this.formsSwitchable = formsSwitchable;
    }

    public int getGenderRate() {
        return genderRate;
    }

    public void setGenderRate(int genderRate) {
        this.genderRate = genderRate;
    }

    public List<Genera> getGenera() {
        return genera;
    }

    public void setGenera(List<Genera> genera) {
        this.genera = genera;
    }

    public Generation getGeneration() {
        return generation;
    }

    public void setGeneration(Generation generation) {
        this.generation = generation;
    }

    public GrowthRate getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(GrowthRate growthRate) {
        this.growthRate = growthRate;
    }

    public Habitat getHabitat() {
        return habitat;
    }

    public void setHabitat(Habitat habitat) {
        this.habitat = habitat;
    }

    public boolean getHasGenderDifferences() {
        return hasGenderDifferences;
    }

    public void setHasGenderDifferences(boolean hasGenderDifferences) {
        this.hasGenderDifferences = hasGenderDifferences;
    }

    public int getHatchCounter() {
        return hatchCounter;
    }

    public void setHatchCounter(int hatchCounter) {
        this.hatchCounter = hatchCounter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getIsBaby() {
        return isBaby;
    }

    public void setIsBaby(boolean isBaby) {
        this.isBaby = isBaby;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Names> getNames() {
        return names;
    }

    public void setNames(List<Names> names) {
        this.names = names;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<PalParkEncounters> getPalParkEncounters() {
        return palParkEncounters;
    }

    public void setPalParkEncounters(List<PalParkEncounters> palParkEncounters) {
        this.palParkEncounters = palParkEncounters;
    }

    public List<PokedexNumbers> getPokedexNumbers() {
        return pokedexNumbers;
    }

    public void setPokedexNumbers(List<PokedexNumbers> pokedexNumbers) {
        this.pokedexNumbers = pokedexNumbers;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public List<Varieties> getVarieties() {
        return varieties;
    }

    public void setVarieties(List<Varieties> varieties) {
        this.varieties = varieties;
    }

}