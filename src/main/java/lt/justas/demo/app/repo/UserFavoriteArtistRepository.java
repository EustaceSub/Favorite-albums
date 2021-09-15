package lt.justas.demo.app.repo;

import lt.justas.demo.model.entity.UserFavoriteArtist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserFavoriteArtistRepository extends JpaRepository<UserFavoriteArtist, Long> {
    Optional<UserFavoriteArtist> findUserFavoriteArtistByUserId(Long userId);
}
