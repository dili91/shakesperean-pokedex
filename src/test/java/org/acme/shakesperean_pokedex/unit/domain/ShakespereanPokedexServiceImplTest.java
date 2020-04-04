package org.acme.shakesperean_pokedex.unit.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.acme.shakesperean_pokedex.domain.DomainException;
import org.acme.shakesperean_pokedex.domain.PokemonItem;
import org.acme.shakesperean_pokedex.domain.service.ShakespereanPokedexServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Shakesperean pokedex service test")
public class ShakespereanPokedexServiceImplTest {

    private static final String A_POKEMON_NAME = "charizard";
    private static final String A_POKEMON_DESCRIPTION = "a description";
    private static final String A_POKEMON_TRANSLATED_DESCRIPTION = "a translated description";        
    private static final PokemonItem A_POKEMON_ITEM = PokemonItem.Builder.aPokemonItem().withName(A_POKEMON_NAME)
            .withDescription(A_POKEMON_DESCRIPTION).build();

    @Test
    @DisplayName("should get a shakesperean pokedex item")
    public void shouldGetAShakespereanPokedexItem(){
        var sut = new ShakespereanPokedexServiceImpl(name -> Optional.of(A_POKEMON_ITEM), description -> Optional.of(A_POKEMON_TRANSLATED_DESCRIPTION));
        
        var shakespereanPokedexItem = sut.getByName(A_POKEMON_NAME);

        assertEquals(A_POKEMON_NAME, shakespereanPokedexItem.getName());
        assertEquals(A_POKEMON_DESCRIPTION, shakespereanPokedexItem.getDescription());
        assertEquals(A_POKEMON_TRANSLATED_DESCRIPTION, shakespereanPokedexItem.getTranslatedDescription());
    }

    @Test
    @DisplayName("should throw a domain exception if pokemon is not found")
    public void shouldThrowADomainExceptionIfPokemonIsNotFound(){
        var sut = new ShakespereanPokedexServiceImpl(name -> Optional.empty(), description -> Optional.empty());

        assertThrows(DomainException.class, ()->sut.getByName(A_POKEMON_NAME));
    }

    @Test
    @DisplayName("should throw a domain exception if translation is not avaiable")
    public void shouldThrowADomainExceptionIfTranslationNotAvaiable(){
        var sut = new ShakespereanPokedexServiceImpl(name -> Optional.of(A_POKEMON_ITEM), description -> Optional.empty());

        assertThrows(DomainException.class, ()->sut.getByName(A_POKEMON_NAME));
    }
}