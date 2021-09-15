package lt.justas.demo.app.config;

import lt.justas.demo.integration.itunes.fetch.ItunesArtistsFetcher;
import lt.justas.demo.integration.itunes.parse.ItunesArtistsDataParser;
import org.junit.jupiter.api.Assertions;
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

    @InjectMocks
    private ItunesDataLoader itunesDataLoader;

    @Test
    public void itunesArtisDataLoaderFetchesAndParsesData() {
        when(itunesArtistsFetcher.fetchData("abba"))
                .thenReturn("success");
        var result = itunesDataLoader.loadArtistsByName("abba");
        assertEquals(0, result.size());
        verify(itunesArtistsFetcher).fetchData("abba");
        verify(itunesArtistsDataParser).parseData("success");
    }
}
