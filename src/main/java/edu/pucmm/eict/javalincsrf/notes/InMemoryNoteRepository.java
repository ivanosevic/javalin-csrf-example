package edu.pucmm.eict.javalincsrf.notes;

import com.github.javafaker.Faker;

import java.time.Instant;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryNoteRepository implements NoteRepository {

    private final List<Note> notes;

    public InMemoryNoteRepository() {
        this.notes = new CopyOnWriteArrayList<>();
        addRandomDataForTesting();
    }

    private void addRandomDataForTesting() {
        int numberOfRandomData = 15;
        for(int i = 0; i < numberOfRandomData; i++) {
            add(Faker.instance().lebowski().character(), Faker.instance().lebowski().quote());
        }
    }

    @Override
    public List<Note> findAll() {
        notes.sort(Comparator.comparing(Note::createdAt));
        Collections.reverse(notes);
        return notes;
    }

    @Override
    public void add(String title, String message) {
        var id = UUID.randomUUID().toString().replace("-", "");
        notes.add(new Note(id, title, message, Instant.now()));
    }
}
