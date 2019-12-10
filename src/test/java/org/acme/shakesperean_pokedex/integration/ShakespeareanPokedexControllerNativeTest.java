package org.acme.shakesperean_pokedex.integration;

import io.quarkus.test.junit.NativeImageTest;
import org.junit.jupiter.api.Disabled;

@NativeImageTest
//@Tag("IntegrationTest")
@Disabled("todo enable again")
public class ShakespeareanPokedexControllerNativeTest extends ShakespeareanPokedexControllerTest {
    // Execute the same tests but in native mode.
}