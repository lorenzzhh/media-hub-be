package ch.axa.mediahubbe.controller;

import ch.axa.mediahubbe.Service.UserProfileService;
import ch.axa.mediahubbe.entity.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/me/profile")
public class UserProfileController {
    private final UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public UserProfile getOwnProfile(Authentication authentication) {
        return userProfileService.getOwnProfile(authentication.getName());
    }

//    @PutMapping
//    @PreAuthorize("hasRole('USER')")
//    public UserProfile updateOwnProfile(
//            @RequestBody UserProfile request,
//            Authentication authentication) {
//        return userProfileService.updateOwnProfile(
//                authentication.getName(), request);
//    }
}