package lt.justas.demo.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.justas.demo.integration.itunes.fetch.ItunesArtistTopAlbumsFetcher;
import lt.justas.demo.integration.itunes.fetch.ItunesArtistsFetcher;
import lt.justas.demo.model.dto.AlbumDTO;
import lt.justas.demo.model.dto.ArtistDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static lt.justas.demo.integration.itunes.parse.DataParserTestUtil.readJsonFromFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
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

    @Test
    public void retrieveDataAboutAbbaCorrectly() throws Exception {
        when(itunesArtistsFetcher.fetchData("abba"))
                .thenReturn(readJsonFromFile("lt/justas/demo/integration/itunes/artistsData.json"));

        var resultAsString = mockMvc.perform(get("/api/search")
                        .param("artistName", "abba")
                ).andExpect(status().isOk())
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

        when(itunesArtistTopAlbumsFetcher.fetchData(2L))
                .thenReturn(readJsonFromFile("lt/justas/demo/integration/itunes/artistTop5FavoriteAlbums.json"));

        var resultAsString = mockMvc.perform(get("/api/user/1/favorite-artist-top-albums"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        var result = objectMapper.readValue(resultAsString, AlbumDTO[].class);
        assertEquals(5, result.length);
    }
}
