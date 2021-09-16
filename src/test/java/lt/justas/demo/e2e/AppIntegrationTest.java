package lt.justas.demo.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.justas.demo.integration.itunes.fetch.ItunesArtistTopAlbumsFetcher;
import lt.justas.demo.integration.itunes.fetch.ItunesArtistsFetcher;
import lt.justas.demo.model.dto.AlbumDTO;
import lt.justas.demo.model.dto.ArtistDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;

import static lt.justas.demo.integration.itunes.parse.DataParserTestUtil.readJsonFromFile;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AppIntegrationTest {
    @MockBean
    ItunesArtistsFetcher itunesArtistsFetcher;
    @MockBean
    ItunesArtistTopAlbumsFetcher itunesArtistTopAlbumsFetcher;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    CacheManager cacheManager;

    @BeforeEach
    public void setUp() throws IOException {
        when(itunesArtistsFetcher.fetchData("abba"))
                .thenReturn(readJsonFromFile("lt/justas/demo/integration/itunes/artistsData.json"));

        when(itunesArtistTopAlbumsFetcher.fetchData(2L))
                .thenReturn(readJsonFromFile("lt/justas/demo/integration/itunes/artistTop5FavoriteAlbums.json"));

        clearItunesArtistsCache();
    }

    @Test
    public void retrieveDataAboutAbbaCorrectly() throws Exception {

        var resultAsString = searchArtistsByAbbaName()
                .andReturn()
                .getResponse()
                .getContentAsString();
        var result = objectMapper.readValue(resultAsString, ArtistDTO[].class);
        assertEquals(50, result.length);
    }

    @Test
    public void retrieveDataAboutFavoriteArtistWithoutFavoriteArtistFail() throws Exception {
        mockMvc.perform(get("/api/user/999/favorite-artist-top-albums"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User 999 does not have favorite artist"));
    }

    @Test
    public void addArtistToFavoriteAndGetArtistTopAlbums() throws Exception {
        mockMvc.perform(post("/api/user/favorite/artist")
                        .param("userId", "1")
                        .param("artistId", "2")
                )
                .andExpect(status().isCreated());

        var resultAsString = mockMvc.perform(get("/api/user/1/favorite-artist-top-albums"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        var result = objectMapper.readValue(resultAsString, AlbumDTO[].class);
        assertEquals(5, result.length);
    }

    @Test
    public void itunesArtistsSearchedByNameAreCached() throws Exception {
        assertNull(cacheManager.getCache("itunes-artists").get("abba"));
        searchArtistsByAbbaName()
                .andReturn();
        assertNotNull(cacheManager.getCache("itunes-artists").get("abba"));
        searchArtistsByAbbaName()
                .andReturn();

        // Without cache we would call this method twice
        verify(itunesArtistsFetcher, times(1)).fetchData("abba");
    }

    private void clearItunesArtistsCache() {
        cacheManager.getCache("itunes-artists")
                .clear();
    }

    private ResultActions searchArtistsByAbbaName() throws Exception {
        return mockMvc.perform(get("/api/search")
                .param("artistName", "abba")
        ).andExpect(status().isOk());
    }
}
