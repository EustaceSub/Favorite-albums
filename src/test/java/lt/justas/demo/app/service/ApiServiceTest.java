package lt.justas.demo.app.service;

import lt.justas.demo.app.config.ItunesDataLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ApiServiceTest {

    @Mock
    private ItunesDataLoader itunesDataLoader;

    @InjectMocks
    ApiService apiService;

    @Test
    public void searchByNamesMethodTakesAndParsesDataCorrectly() {
        apiService.searchArtistsByName("abba");
        verify(itunesDataLoader).loadArtistsByName("abba");
    }
}
