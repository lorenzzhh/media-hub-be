package ch.axa.mediahubbe.Service;

import ch.axa.mediahubbe.Repo.AppUserRepository;
import ch.axa.mediahubbe.dtos.LoginRequestDto;
import ch.axa.mediahubbe.dtos.LoginResponseDto;
import ch.axa.mediahubbe.entity.AppUser;
import ch.axa.mediahubbe.entity.PendingRegistration;
import ch.axa.mediahubbe.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final AppUserRepository appUserRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Autowired
    public AuthenticationService(AppUserRepository appUserRepository, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.appUserRepository = appUserRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public Optional<AppUser> findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }


    public AppUser save(PendingRegistration pendingRegistration) {
        AppUser appUser = new AppUser(
                pendingRegistration.getUsername(),
                pendingRegistration.getPasswordHash(),
                pendingRegistration.getRole()
        );

        return appUserRepository.save(appUser);
    }


    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.username(),
                        loginRequestDto.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtService.generateToken(authentication);

        return new LoginResponseDto(token);
    }


}
