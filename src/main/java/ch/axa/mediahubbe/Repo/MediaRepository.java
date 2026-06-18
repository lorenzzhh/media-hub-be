package ch.axa.mediahubbe.Repo;

import ch.axa.mediahubbe.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {

}
