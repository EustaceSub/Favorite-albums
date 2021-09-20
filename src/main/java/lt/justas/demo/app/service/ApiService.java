package lt.justas.demo.app.service;

import lt.justas.demo.app.config.ItunesDataLoader;
import lt.justas.demo.app.exception.UserNoFavoriteArtistException;
import lt.justas.demo.app.repo.UserFavoriteArtistRepository;
import lt.justas.demo.model.dto.AlbumDTO;
import lt.justas.demo.model.dto.ArtistDTO;
import lt.justas.demo.model.entity.UserFavoriteArtist;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ApiService {
    private final UserFavoriteArtistRepository userFavoriteArtistRepository;
    private final ItunesDataLoader itunesDataLoader;

    public ApiService(UserFavoriteArtistRepository userFavoriteArtistRepository, ItunesDataLoader itunesDataLoader) {
        this.userFavoriteArtistRepository = userFavoriteArtistRepository;
        this.itunesDataLoader = itunesDataLoader;
    }

    public List<ArtistDTO> searchArtistsByName(String artistName) {
        return itunesDataLoader.loadArtistsByName(artistName);
    }

    public void modifyUserFavoriteArtist(Long userId, Long artistId) {
        var userWithFavoriteArtist = userFavoriteArtistRepository
                .findUserFavoriteArtistByUserId(userId);

        if (userWithFavoriteArtist.isPresent()) {
            var userFavoriteArtist = userWithFavoriteArtist.get();
            userFavoriteArtist.setAmgArtistId(artistId);
            userFavoriteArtistRepository.save(userFavoriteArtist);
        } else {
            var newUserFavoriteArtist = new UserFavoriteArtist()
                    .setUserId(userId)
                    .setAmgArtistId(artistId);
            userFavoriteArtistRepository.save(newUserFavoriteArtist);
        }
    }

    public Collection<AlbumDTO> getFavoriteArtistTopAlbums(Long userId) {
        var userWithFavoriteArtist = userFavoriteArtistRepository
                .findUserFavoriteArtistByUserId(userId);
        var favoriteArtistId = userWithFavoriteArtist
                .map(UserFavoriteArtist::getAmgArtistId)
                .orElseThrow(() -> new UserNoFavoriteArtistException(userId));

        return itunesDataLoader.loadArtistTopAlbums(favoriteArtistId);
    }
}
