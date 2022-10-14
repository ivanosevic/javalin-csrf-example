package edu.pucmm.eict.javalincsrf.security.csrf;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class CsrfFilter {

    private final CsrfRepository csrfRepository;

    public CsrfFilter(CsrfRepository csrfRepository) {
        this.csrfRepository = csrfRepository;
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
        var sentToken = ctx.formParam("_csrf");
        if ((sentToken == null) || !sentToken.contentEquals(validToken)) {
            throw new InvalidCsrfTokenException("An Invalid CSRF was sent.");
        }
    }

    /**
     * Will handle the response when the exception InvalidCsrfTokenException is thrown in a Handler.
     *
     * @param ex  InvalidCsrfTokenException
     * @param ctx Javalin's Context
     */
    public void handleInvalidCsrfTokenException(InvalidCsrfTokenException ex, Context ctx) {
        ctx.status(HttpStatus.FORBIDDEN).result(ex.getMessage());
    }
}
