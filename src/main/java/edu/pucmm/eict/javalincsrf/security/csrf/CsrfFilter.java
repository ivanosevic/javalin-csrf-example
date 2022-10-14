package edu.pucmm.eict.javalincsrf.security.csrf;

import io.javalin.http.Context;

public class CsrfFilter {

    private final CsrfRepository csrfRepository;
    private final CsrfReader csrfReader;

    public CsrfFilter(CsrfRepository csrfRepository, CsrfReader csrfReader) {
        this.csrfRepository = csrfRepository;
        this.csrfReader = csrfReader;
    }

    /**
     * Generates a new token. It's recommended to call this per-request,
     * rather than per-session.
     *
     * @param ctx Javalin's Context
     */
    public void generateToken(Context ctx) {
        csrfRepository.newToken(ctx);
    }

    /**
     * Will validate against the token sent by the user. In case the token is invalid, the request
     * will be stopped.
     *
     * @param ctx Javalin's Context
     * @throws InvalidCsrfTokenException If the send token is invalid
     */
    public void validateToken(Context ctx) {
        var validToken = csrfRepository.get(ctx);
        var sentToken = csrfReader.retrieveToken(ctx);
        if (sentToken == null || sentToken.isBlank()) {
            throw new InvalidCsrfTokenException("An Invalid CSRF was sent.");
        }

        if(!sentToken.equalsIgnoreCase(validToken)) {
            throw new InvalidCsrfTokenException("An Invalid CSRF was sent.");
        }
    }
}
