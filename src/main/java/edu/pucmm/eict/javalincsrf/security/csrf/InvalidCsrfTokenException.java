package edu.pucmm.eict.javalincsrf.security.csrf;

public class InvalidCsrfTokenException extends RuntimeException {
    public InvalidCsrfTokenException(String message) {
        super(message);
    }
}
