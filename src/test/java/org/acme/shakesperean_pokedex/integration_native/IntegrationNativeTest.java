package org.acme.shakesperean_pokedex.integration_native;

import io.quarkus.test.junit.NativeImageTest;
import org.acme.shakesperean_pokedex.integration.IntegrationTest;

@NativeImageTest
public class IntegrationNativeTest extends IntegrationTest {
    // Execute the same tests but in native mode.
}