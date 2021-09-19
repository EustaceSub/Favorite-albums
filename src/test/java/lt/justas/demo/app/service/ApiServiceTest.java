package lt.justas.demo.app.service;

import lt.justas.demo.app.config.ItunesDataLoader;
import lt.justas.demo.app.exception.UserNoFavoriteArtistException;
import lt.justas.demo.app.repo.UserFavoriteArtistRepository;
import lt.justas.demo.model.entity.UserFavoriteArtist;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApiServiceTest {

    @Mock
    private ItunesDataLoader itunesDataLoader;

    @Mock
    private UserFavoriteArtistRepository userFavoriteArtistRepository;

    @InjectMocks
    ApiService apiService;

    @Test
    public void searchByNamesMethodTakesAndParsesDataCorrectly() {
        apiService.searchArtistsByName("abba");
        verify(itunesDataLoader).loadArtistsByName("abba");
    }

    @Test
    public void userSavesHisFavoriteArtistFirstTimeCorrectly() {
        when(userFavoriteArtistRepository.findUserFavoriteArtistByUserId(1L))
                .thenReturn(empty());

        apiService.modifyUserFavoriteArtist(1L, 2L);

        verify(userFavoriteArtistRepository).save(
                new UserFavoriteArtist()
                        .setUserId(1L)
                        .setAmgArtistId(2L)
        );
    }

    @Test
    public void userModifiesHisFavoriteArtist() {
        when(userFavoriteArtistRepository.findUserFavoriteArtistByUserId(1L))
                .thenReturn(Optional.of(
                                new UserFavoriteArtist()
                                        .setId(3L)
                                        .setUserId(1L)
                                        .setAmgArtistId(8L)
                        )
                );
        apiService.modifyUserFavoriteArtist(1L, 2L);
        verify(userFavoriteArtistRepository).save(
                new UserFavoriteArtist()
                        .setId(3L)
                        .setUserId(1L)
                        .setAmgArtistId(2L)
        );
    }

    @Test
    public void userWithoutFavoriteArtistTriesToGetTopAlbums() {
        when(userFavoriteArtistRepository.findUserFavoriteArtistByUserId(1L))
                .thenReturn(Optional.empty());
        var caughtException = assertThrows(
                UserNoFavoriteArtistException.class,
                () -> apiService.getFavoriteArtistTopAlbums(1L)
        );
        assertEquals("User 1 does not have favorite artist", caughtException.getMessage());
    }

    @Test
    public void userWithFavoriteArtistTriesToGetFavoriteAlbums() {
        when(userFavoriteArtistRepository.findUserFavoriteArtistByUserId(1L))
                .thenReturn(Optional.of(
                                new UserFavoriteArtist()
                                        .setId(3L)
                                        .setUserId(1L)
                                        .setAmgArtistId(8L)
                        )
                );
        var result = apiService.getFavoriteArtistTopAlbums(1L);
        assertTrue(result.isEmpty());
        verify(itunesDataLoader).loadArtistTopAlbums(8L);
    }
}

