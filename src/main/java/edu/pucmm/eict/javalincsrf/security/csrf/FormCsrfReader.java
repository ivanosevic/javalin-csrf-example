package edu.pucmm.eict.javalincsrf.security.csrf;

import io.javalin.http.Context;

public class FormCsrfReader implements CsrfReader {

    @Override
    public String retrieveToken(Context ctx) {
        return ctx.formParam("_csrf");
    }
}
