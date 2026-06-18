package ch.axa.mediahubbe.Service;

import ch.axa.mediahubbe.Repo.AppUserRepository;
import ch.axa.mediahubbe.entity.AppUser;
import ch.axa.mediahubbe.entity.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserProfileService {
    private final AppUserRepository userRepository; //todo evtl ändern
    @Autowired
    public UserProfileService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserProfile getOwnProfile(String username) {
        return userRepository.findByUsername(username)
                .map(AppUser::getUserProfile)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Profile not found"));
    }
}