package lt.justas.demo.app.config;

import lt.justas.demo.integration.itunes.fetch.ItunesArtistTopAlbumsFetcher;
import lt.justas.demo.integration.itunes.fetch.ItunesArtistsFetcher;
import lt.justas.demo.integration.itunes.parse.ItunesArtistTopAlbumsParser;
import lt.justas.demo.integration.itunes.parse.ItunesArtistsDataParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItunesDataLoaderTest {

    @Mock
    private ItunesArtistsFetcher itunesArtistsFetcher;

    @Mock
    private ItunesArtistsDataParser itunesArtistsDataParser;

    @Mock
    private ItunesArtistTopAlbumsFetcher itunesArtistTopAlbumsFetcher;

    @Mock
    private ItunesArtistTopAlbumsParser itunesArtistTopAlbumsParser;

    @InjectMocks
    private ItunesDataLoader itunesDataLoader;

    @Test
    public void itunesDataLoaderFetchesAndParsesArtistDataByArtistName() {
        when(itunesArtistsFetcher.fetchData("abba"))
                .thenReturn("success");
        var result = itunesDataLoader.loadArtistsByName("abba");
        assertEquals(0, result.size());
        verify(itunesArtistsFetcher).fetchData("abba");
        verify(itunesArtistsDataParser).parseData("success");
    }

    @Test
    public void itunesDataLoadFetchesAndParsesArtistTopAlbumsByArtistId() {
        when(itunesArtistTopAlbumsFetcher.fetchData(1L))
                .thenReturn("success");

        var result = itunesDataLoader.loadTopArtistAlbums(1L);
        assertEquals(0, result.size());
        verify(itunesArtistTopAlbumsFetcher).fetchData(1L);
        verify(itunesArtistTopAlbumsParser).parseData("success");
    }
}
