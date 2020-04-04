package org.acme.shakesperean_pokedex.domain.repository;

import java.util.Optional;

import org.acme.shakesperean_pokedex.domain.PokemonItem;

public interface PokemonRepository {

    /**
     * Returns a pokemon given its name
     * @param name the name of the pokemon to look for
     * @return a pokemon if any
     */
    Optional<PokemonItem> getByName(String name);
}