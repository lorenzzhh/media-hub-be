package ch.axa.mediahubbe.dtos;

import ch.axa.mediahubbe.security.Role;

public record RegisterDto(String username,
                          String email, String password, Role role) {
}
