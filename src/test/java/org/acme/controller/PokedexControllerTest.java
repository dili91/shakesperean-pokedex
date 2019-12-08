package org.acme.controller;

import org.acme.shakesperean_pokedex.controller.PokedexController;
import org.acme.shakesperean_pokedex.dto.poke_api.PokemonSpecies;
import org.acme.shakesperean_pokedex.service.PokeApiService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PokedexControllerTest {
    //todo complete

    private static final String A_RESPONSE = "andrea";
    @Mock
    private PokeApiService pokeApiService;

    @InjectMocks
    private PokedexController pokedexController;

    @Test
    @DisplayName("should return a hello message")
    public void testHello() {
        when(pokeApiService.getPokemonSpecies(anyString())).thenReturn(new PokemonSpecies());

        var response = pokedexController.getDescription("");

        verify(pokeApiService, times(1)).getPokemonSpecies(anyString());
        assertEquals(A_RESPONSE, response);
    }

}