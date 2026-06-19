package ch.axa.mediahubbe.controller;

import ch.axa.mediahubbe.Service.AuthenticationService;
import ch.axa.mediahubbe.Service.MailService;
import ch.axa.mediahubbe.Service.RegistrierungsService;
import ch.axa.mediahubbe.dtos.LoginRequestDto;
import ch.axa.mediahubbe.dtos.LoginResponseDto;
import ch.axa.mediahubbe.dtos.RegisterDto;
import ch.axa.mediahubbe.entity.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final RegistrierungsService registrierungsService;
    private final MailService mailService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, RegistrierungsService registrierungsService, MailService mailService) {
        this.authenticationService = authenticationService;
        this.registrierungsService = registrierungsService;
        this.mailService = mailService;
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return authenticationService.login(loginRequestDto);
    }

    @GetMapping("/protected")
    @PreAuthorize("hasAnyRole('TEACHER', 'STUDENT')")
    public String protectedEndpoint() {
        return "Dieser Endpunkt ist für Teacher und Student geschützt.";
    }

    @GetMapping("/teacher")
    @PreAuthorize("hasRole('TEACHER')")
    public String teacherEndpoint() {
        return "Dieser Endpunkt ist nur für Teacher.";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto dto) {
        if (dto.username() == null || dto.password() == null || dto.role() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Pflichtfelder nicht ausgefüllt");
        }

        if (authenticationService.findByUsername(dto.username()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account mit diesem Username existiert schon!");
        }

        try {
            UUID token = registrierungsService.starteRegistrierung(dto);
            mailService.sendRegistrationVerification(dto.username(), token);

            return new ResponseEntity<String>(token.toString(), HttpStatus.CREATED); // 210 Created

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ein unerwarteter Fehler ist aufgetreten (Controller)."); // 500
        }
    }

    @GetMapping("/register/confirm/{token}")
    public ResponseEntity<?> confirm(@PathVariable UUID token) {
        try {
            return registrierungsService.finde(token).map(pendingRegistration -> {

                Instant einTagZuvor = Instant.now().minus(24, ChronoUnit.HOURS);
                boolean istAbgelaufen = pendingRegistration.getCreatedAt().isBefore(einTagZuvor);

                if (istAbgelaufen) {
                    registrierungsService.removePendingRegistration(token);
                    return ResponseEntity.status(HttpStatus.GONE).body("Der Bestätigungslink ist abgelaufen (24h überschritten)."); // 410
                }

                registrierungsService.removePendingRegistration(token);
                AppUser appUser = authenticationService.save(pendingRegistration);

                return new ResponseEntity<>(appUser, HttpStatus.CREATED);

            }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bestätigungslink unbekannt.")); // 404

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ein unerwarteter Fehler ist aufgetreten."); // 500
        }
    }
}
