package ch.axa.mediahubbe.Service;

import ch.axa.mediahubbe.Repo.AppUserRepository;
import ch.axa.mediahubbe.dtos.RegisterDto;
import ch.axa.mediahubbe.entity.PendingRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RegistrierungsService {
    // TODO: evtl. HashMap einsetzen
    private final List<PendingRegistration> pending = new ArrayList<>();

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrierungsService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UUID starteRegistrierung(RegisterDto dto) {
        UUID registrationToken = UUID.randomUUID();
        String passwordHash = passwordEncoder.encode(dto.password());

        pending.add(new PendingRegistration( dto.username(), passwordHash, dto.role(), registrationToken, Instant.now()));
        return registrationToken; // wird mit dem Link verwendet
    }

    public Optional<PendingRegistration> finde(UUID token) {
        return pending.stream()
                .filter(p -> p.getToken().equals(token))
                .findFirst();
    }

    public void removePendingRegistration(UUID token) {
        pending.removeIf(t -> t.getToken().equals(token));
    }
}
