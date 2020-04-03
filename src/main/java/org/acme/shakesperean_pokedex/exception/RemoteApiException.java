package org.acme.shakesperean_pokedex.exception;

import javax.ws.rs.WebApplicationException;

/**
 * Exception class that wraps instances of type WebApplicationException.
 * Currently required due to a Quarkus open issue
 *
 * @see "https://github.com/quarkusio/quarkus/issues/4031"
 */
public class RemoteApiException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public RemoteApiException(WebApplicationException wrappedException) {
        super(wrappedException);
    }

    public WebApplicationException getWrappedException() {
        return (WebApplicationException) this.getCause();
    }
}
