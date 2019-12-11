package org.acme.shakesperean_pokedex.util;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockServerExtension.class)
public abstract class RemoteApiTest {

    //holds the mock server for this api
    protected WireMockServer mockServer;

    /**
     * Called by Mock api extension
     *
     * @param mockServer wiremock server instan
     */
    public void setMockServer(WireMockServer mockServer) {
        this.mockServer = mockServer;
    }

}
