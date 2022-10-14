package edu.pucmm.eict.javalincsrf.security.csrf;

import io.javalin.http.Context;

public interface CsrfReader {
    String retrieveToken(Context ctx);
}
