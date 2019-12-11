package org.acme.shakesperean_pokedex.util;

public class Configuration {

    public static final int MOCK_SERVER_PORT_NUMBER = 8989;

    public static final String POKE_API_SPECIES_PATH = "/api/v2/pokemon-species/.*";
    public static final String POKEAPI_SPECIES_CHARIZARD_JSON_FILE = "pokeapi_species.charizard.json";

    public static final String TRANSLATION_API_PATH = "/translate/shakespeare.json";
    public static final String TRANSLATION_API_SUN_IS_SHINING_JSON_FILE = "translation_api_shakespeare.sun_is_shining.json";
    public static final String TRANSLATION_API_CHARIZARD_JSON_FILE = "translation_api_shakespeare.charizard.json";

    private Configuration() {
        //util class
    }
}
