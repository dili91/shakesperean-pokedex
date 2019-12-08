package org.acme;

import io.quarkus.test.junit.NativeImageTest;
import org.acme.controller.PokedexControllerTest;

@NativeImageTest
public class NativePokedexControllerIT extends PokedexControllerTest {

    // Execute the same tests but in native mode.
    //todo
}