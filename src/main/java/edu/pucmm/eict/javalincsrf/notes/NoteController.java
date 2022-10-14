package edu.pucmm.eict.javalincsrf.notes;

import edu.pucmm.eict.javalincsrf.security.csrf.CsrfRepository;
import io.javalin.http.Context;

import java.util.HashMap;

public class NoteController {

    private final NoteRepository noteRepository;
    private final CsrfRepository csrfRepository;

    public NoteController(NoteRepository noteRepository, CsrfRepository csrfRepository) {
        this.noteRepository = noteRepository;
        this.csrfRepository = csrfRepository;
    }

    public void showNotesPage(Context ctx) {
        var notes = noteRepository.findAll();
        var csrfToken = csrfRepository.get(ctx);
        Boolean wasNoteCreated = ctx.consumeSessionAttribute("noteCreated");
        var viewData = new HashMap<String, Object>();
        viewData.put("csrfToken", csrfToken);
        viewData.put("notes", notes);
        viewData.put("wasNoteCreated", wasNoteCreated);
        ctx.render("templates/notes.ftl", viewData);
    }

    public void processNewPage(Context ctx) {
        String title = ctx.formParamAsClass("noteTitle", String.class).get();
        String message = ctx.formParamAsClass("noteMessage", String.class).get();
        noteRepository.add(title, message);
        ctx.sessionAttribute("noteCreated", Boolean.TRUE);
        ctx.redirect("/notes");
    }
}
