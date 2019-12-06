package org.acme.shakesperean_pokedex.service;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PokeApiService {

    public String getDescription(String pokemonName){
        return "whatever";
    }
}
