package org.acme.shakesperean_pokedex.exception;

import javax.ws.rs.WebApplicationException;

/**
 * wrapper required as workaroung for this currently open issue https://github.com/quarkusio/quarkus/issues/4031
 */
public class RemoteApiException extends RuntimeException {

    public RemoteApiException(WebApplicationException wrappedException) {
        super(wrappedException);
    }

    public WebApplicationException getWrappedException() {
        return (WebApplicationException) this.getCause();
    }
}
