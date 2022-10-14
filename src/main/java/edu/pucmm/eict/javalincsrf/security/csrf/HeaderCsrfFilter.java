package edu.pucmm.eict.javalincsrf.security.csrf;

import io.javalin.http.Context;

public class HeaderCsrfFilter implements CsrfReader {

    @Override
    public String retrieveToken(Context ctx) {
        return ctx.header("X-XSRF-TOKEN");
    }
}
