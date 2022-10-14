package edu.pucmm.eict.javalincsrf.notes;

import java.util.List;

public interface NoteRepository {
    List<Note> findAll();
    void add(String title, String message);
}
