package ch.axa.mediahubbe.entity;

public class InvalidProfileStateException extends RuntimeException {
    public InvalidProfileStateException(String message) {
        super(message);
    }
}
