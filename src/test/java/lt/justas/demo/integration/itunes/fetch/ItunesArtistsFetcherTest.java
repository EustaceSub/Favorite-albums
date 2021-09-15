package lt.justas.demo.integration.itunes.fetch;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItunesArtistsFetcherTest {
    @Mock
    RestTemplate restTemplate;

    @Test
    public void itunesArtistFetchesFromCorrectUrl() {
        var itunesArtistsFetcher = new ItunesArtistsFetcher(restTemplate, "test.com");
        when(restTemplate.getForObject("https://test.com/search?term=abba&entity=allArtist&limit=200", String.class))
                .thenReturn("Success");
        assertEquals("Success", itunesArtistsFetcher.fetchData("abba"));
    }
}
