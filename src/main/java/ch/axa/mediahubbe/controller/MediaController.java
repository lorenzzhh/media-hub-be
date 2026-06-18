package ch.axa.mediahubbe.controller;

import ch.axa.mediahubbe.Service.MediaService;
import ch.axa.mediahubbe.dtos.MediaDto;
import jakarta.persistence.OptimisticLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/media")
@CrossOrigin("*")
public class MediaController {

    private final MediaService mediaService;

    @Autowired
    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> edit(
            @PathVariable Long id,
            @RequestBody MediaDto mediaDto) {

        try{
            return ResponseEntity.ok(mediaService.edit(id, mediaDto));
        }catch (OptimisticLockException e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Die Datei wurde inzwischen von einem anderen Benutzer geändert.");
        }
    }
}
