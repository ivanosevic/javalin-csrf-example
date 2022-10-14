package edu.pucmm.eict.javalincsrf.security.csrf.plugin;

import edu.pucmm.eict.javalincsrf.security.csrf.CsrfFilter;
import edu.pucmm.eict.javalincsrf.security.csrf.CsrfRepository;
import io.javalin.Javalin;
import io.javalin.http.HandlerType;
import io.javalin.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CsrfPlugin implements Plugin {

    private final CsrfRepository csrfRepository;
    private final CsrfFilter csrfFilter;

    public CsrfPlugin() {
        this.csrfRepository = new CsrfRepository();
        this.csrfFilter = new CsrfFilter(csrfRepository);
    }

    @Override
    public void apply(@NotNull Javalin javalin) {
        javalin.before(ctx -> {
            var safeMethods = List.of(HandlerType.GET, HandlerType.OPTIONS, HandlerType.HEAD);
            var changingStateMethods = List.of(HandlerType.POST, HandlerType.DELETE, HandlerType.PUT, HandlerType.PATCH);
            if (safeMethods.contains(ctx.method())) {
                csrfFilter.generateToken(ctx);
            }

            if (changingStateMethods.contains(ctx.method())) {
                csrfFilter.validateToken(ctx);
            }
        });
    }
}
