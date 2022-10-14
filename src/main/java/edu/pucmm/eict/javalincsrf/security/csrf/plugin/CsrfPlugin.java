package edu.pucmm.eict.javalincsrf.security.csrf.plugin;

import edu.pucmm.eict.javalincsrf.security.csrf.CsrfFilter;
import edu.pucmm.eict.javalincsrf.security.csrf.CsrfRepository;
import io.javalin.Javalin;
import io.javalin.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class CsrfPlugin implements Plugin {

    private final CsrfFilter csrfFilter;
    private final CsrfPluginConfig config;

    public CsrfPlugin() {
        this.config = new CsrfPluginConfig();
        this.csrfFilter = new CsrfFilter(new CsrfRepository(), CsrfReaderFactory.get(config.getCsrfReaderType()));
    }

    @Override
    public void apply(@NotNull Javalin javalin) {
        javalin.before(ctx -> {
            var safeMethods = config.getSafeMethods();
            if (safeMethods.contains(ctx.req().getMethod())) {
                csrfFilter.generateToken(ctx);
                return;
            }

            var changingStateMethods = config.getStateChangingMethods();
            if (changingStateMethods.contains(ctx.req().getMethod())) {
                csrfFilter.validateToken(ctx);
            }
        });
    }
}
