package ch.axa.mediahubbe.dtos;


import ch.axa.mediahubbe.entity.AppUser;

public record MediaDto(Long id,
                       String filename,
                       String type,
                       boolean visibility,
                       AppUser owner,
                       Long version,
                       String title,
                       String description
) {
}
