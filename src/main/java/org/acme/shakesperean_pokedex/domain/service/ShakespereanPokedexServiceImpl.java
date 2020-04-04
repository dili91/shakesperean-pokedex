package org.acme.shakesperean_pokedex.domain.service;

import org.acme.shakesperean_pokedex.domain.DomainException;
import org.acme.shakesperean_pokedex.domain.PokemonItem;
import org.acme.shakesperean_pokedex.domain.ShakespereanPokedexItem;
import org.acme.shakesperean_pokedex.domain.repository.PokemonRepository;
import org.acme.shakesperean_pokedex.domain.repository.TranslationRepository;

public class ShakespereanPokedexServiceImpl implements ShakespereanPokedexService {

    private final PokemonRepository pokemonRepository;

    private final TranslationRepository translationRepository;

    public ShakespereanPokedexServiceImpl(PokemonRepository pokemonRepository,
            TranslationRepository translationRepository) {
        this.pokemonRepository = pokemonRepository;
        this.translationRepository = translationRepository;
    }

    @Override
    public ShakespereanPokedexItem getByName(String pokemonName) {
        PokemonItem pokemon = pokemonRepository.getByName(pokemonName)
                .orElseThrow(() -> new DomainException(String.format("Pokemon %s not found", pokemonName)));

        String translatedDescription = translationRepository.get(pokemon.getDescription())
                .orElseThrow(() -> new DomainException("Translation not available"));

        return ShakespereanPokedexItem.Builder.aShakespereanPokedexItem().withName(pokemon.getName())
                .withDescription(pokemon.getDescription()).withTranslatedDescription(translatedDescription).build();
    }

}