package org.acme.shakesperean_pokedex.util;

public class Configuration {

    public static final int MOCK_SERVER_PORT_NUMBER = 8989;

    public static final String POKE_API_JSON_STUBS_FOLDER = "poke_api";
    public static final String FUN_TRANSLATIONS_API_JSON_STUBS_FOLDER = "fun_translations";

    public static final String POKE_API_SPECIES_PATH = "/api/v2/pokemon-species/.*";
    public static final String POKEAPI_SPECIES_CHARIZARD_JSON_STUB_FILE = "pokemon_species.charizard.json";
    public static final String POKEAPI_SPECIES_CHARIZARD_MISSING_DESCRIPTION_JSON_STUB_FILE = "pokemon_species.charizard.missing_description.json";

    public static final String TRANSLATION_API_PATH = "/translate/shakespeare.json";
    public static final String TRANSLATION_API_SUN_IS_SHINING_JSON_STUB_FILE = "shakespeare.sun_is_shining.json";
    public static final String TRANSLATION_API_CHARIZARD_JSON_STUB_FILE = "shakespeare.charizard.json";

    public static final int RESPONSE_DELAYS_MS = 10000;

    private Configuration() {
        //util class
    }
}
