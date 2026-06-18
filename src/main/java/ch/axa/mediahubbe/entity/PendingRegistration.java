package ch.axa.mediahubbe.entity;

import ch.axa.mediahubbe.security.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PendingRegistration {
    private String username;
    private String passwordHash; // bereits beim Anlegen hashen
    private Role role;
    private UUID token;
    private Instant createdAt; // fuer die Gueltigkeitsdauer
}
