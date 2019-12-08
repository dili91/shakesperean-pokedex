package org.acme.shakesperean_pokedex.controller;

import org.acme.shakesperean_pokedex.dto.PokedexResult;
import org.acme.shakesperean_pokedex.dto.poke_api.PokemonSpecies;
import org.acme.shakesperean_pokedex.service.connector.PokeApiClient;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@Disabled("to enable once controller implementation takes place")
public class ShakespeareanPokedexControllerTest {
    //todo complete

    private static final String A_RESPONSE = "andrea";
    @Mock
    private PokeApiClient pokeApiClient;

    @InjectMocks
    private ShakespeareanPokedexController shakespeareanPokedexController;

    @Test
    public void shouldReturnAPokedexResult() {
        when(pokeApiClient.getPokemonSpecies(anyString())).thenReturn(new PokemonSpecies());

        PokedexResult response = shakespeareanPokedexController.getDescription("");

        verify(pokeApiClient, times(1)).getPokemonSpecies(anyString());
        assertEquals(A_RESPONSE, response);
    }

}