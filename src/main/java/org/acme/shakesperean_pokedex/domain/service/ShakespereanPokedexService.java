package org.acme.shakesperean_pokedex.domain.service;

import org.acme.shakesperean_pokedex.domain.ShakespereanPokedexItem;

public interface ShakespereanPokedexService {

    /**
     * Return a shakesperean pokedex result given a pokemon name
     * 
     * @param pokemonName the pokemon name to look for
     * @return a shakesperean pokedex result made of name, description and
     *         translated description
     */
    ShakespereanPokedexItem getByName(String pokemonName);
}