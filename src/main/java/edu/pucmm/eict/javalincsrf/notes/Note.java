package edu.pucmm.eict.javalincsrf.notes;

import java.time.Instant;

public record Note(String id, String title, String message, Instant createdAt) {
}
