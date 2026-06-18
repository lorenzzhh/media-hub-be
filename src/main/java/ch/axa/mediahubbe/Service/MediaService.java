package ch.axa.mediahubbe.Service;


import ch.axa.mediahubbe.Repo.MediaRepository;
import ch.axa.mediahubbe.dtos.MediaDto;
import ch.axa.mediahubbe.entity.Media;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediaService {

    private final MediaRepository mediaRepository;

    @Autowired
    public MediaService(MediaRepository mediaRepository){
        this.mediaRepository = mediaRepository;
    }

    @Transactional
    public MediaDto edit(Long id, MediaDto dto){

        Media media = mediaRepository.findById(id)
                .orElseThrow();

        media.setVersion(dto.version());

        media.setTitle(dto.title());
        media.setDescription(dto.description());

        mediaRepository.saveAndFlush(media);

        return dto;
    }
}
