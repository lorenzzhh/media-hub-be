package ch.axa.mediahubbe.security;


import ch.axa.mediahubbe.Repo.AppUserRepository;
import ch.axa.mediahubbe.entity.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new User(
                appUser.getUsername(),
                appUser.getPasswordHash(),
                List.of(new SimpleGrantedAuthority("ROLE_" + appUser.getUsername()))
        );
    }
}
