package ch.axa.mediahubbe.config;

import ch.axa.mediahubbe.dtos.ApiError;
import ch.axa.mediahubbe.entity.InvalidProfileStateException;
import ch.axa.mediahubbe.entity.ProfileNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(
            ProfileNotFoundException ex) {
        ApiError error = new ApiError(Instant.now(), 404,
                "PROFILE_NOT_FOUND", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    @ExceptionHandler(InvalidProfileStateException.class)
    public ResponseEntity<ApiError> handleInvalidState(
            InvalidProfileStateException ex) {
        ApiError error = new ApiError(Instant.now(), 409,
                "PROFILE_INVALID_STATE", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
    // Fallback: gibt KEINE internen Details preis
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex) {
        ApiError error = new ApiError(Instant.now(), 500,
                "INTERNAL_ERROR",
                "Ein unerwarteter Fehler ist aufgetreten.");
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}