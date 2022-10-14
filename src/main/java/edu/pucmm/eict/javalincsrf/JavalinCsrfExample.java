package edu.pucmm.eict.javalincsrf;

import edu.pucmm.eict.javalincsrf.notes.InMemoryNoteRepository;
import edu.pucmm.eict.javalincsrf.notes.NoteController;
import edu.pucmm.eict.javalincsrf.security.csrf.CsrfFilter;
import edu.pucmm.eict.javalincsrf.security.csrf.CsrfRepository;
import edu.pucmm.eict.javalincsrf.security.csrf.InvalidCsrfTokenException;
import edu.pucmm.eict.javalincsrf.security.csrf.plugin.CsrfPlugin;
import io.javalin.Javalin;
import io.javalin.rendering.JavalinRenderer;
import io.javalin.rendering.template.JavalinFreemarker;

public class JavalinCsrfExample {
    public static void main(String[] args) {
        var app = Javalin.create(config -> {
            config.showJavalinBanner = true;
            config.staticFiles.enableWebjars();
            config.plugins.register(new CsrfPlugin());
        });

        var csrfRepository = new CsrfRepository();
        var csrfFilter = new CsrfFilter(csrfRepository);
        var noteRepository = new InMemoryNoteRepository();
        var noteController = new NoteController(noteRepository, csrfRepository);

        /*
            Now, we add CSRF protection to our desired endpoints.
            In case of pages that we have forms, it's best to generate
            a new token per-request. On the other hand, POST requests must
            verify the existence of the CSRF token.
         */
        /*app.before("/notes", csrfFilter::generateToken);
        app.before("/notes/new", csrfFilter::validateToken);*/

        app.get("/", ctx -> ctx.redirect("/notes"));
        app.get("/notes", noteController::showNotesPage);
        app.post("/notes/new", noteController::processNewPage);

        app.exception(InvalidCsrfTokenException.class, csrfFilter::handleInvalidCsrfTokenException);

        var defaultFreemarkerConfig = JavalinFreemarker.Companion.defaultFreemarkerEngine();
        JavalinRenderer.register(new JavalinFreemarker(defaultFreemarkerConfig), ".ftl");

        app.start();
    }
}