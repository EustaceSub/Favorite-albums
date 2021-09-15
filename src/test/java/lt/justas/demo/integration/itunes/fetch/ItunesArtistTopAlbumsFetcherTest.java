package lt.justas.demo.integration.itunes.fetch;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItunesArtistTopAlbumsFetcherTest {

    @Mock
    RestTemplate restTemplate;

    @Test
    public void itunesArtistFetchesFromCorrectUrl() {
        var itunesTopAlbumsFetcher = new ItunesArtistTopAlbumsFetcher(restTemplate, "test.com");
        when(restTemplate.getForObject("https://test.com/lookup?amgArtistId=2&entity=album&limit=5", String.class))
                .thenReturn("Success");
        assertEquals("Success", itunesTopAlbumsFetcher.fetchData(2L));
    }

}
