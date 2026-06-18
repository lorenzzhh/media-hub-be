package ch.axa.mediahubbe.Service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MailService {
    public void sendRegistrationVerification(String email, UUID token) {
        String link = "http://localhost:8080/auth/register/confirm/" + token;
        System.out.println("Registrierungslink fuer " + email + ": " + link);
    }
}