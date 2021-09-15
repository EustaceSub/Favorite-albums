package lt.justas.demo.app.controller;


import lt.justas.demo.app.service.ApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ApiController.class)
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApiService apiService;

    @Test
    public void apiSearchEndpointIsExposed() throws Exception {
        mockMvc.perform(get("/api/search")
                .param("artistName", "abba")
        ).andExpect(status().isOk());
        verify(apiService).searchArtistsByName("abba");
    }

    @Test
    public void userFavoriteArtistEndpointExposed() throws Exception {
        mockMvc.perform(post("/api/user/favorite/artist")
                        .param("userId", "1")
                        .param("artistId", "2")
                )
                .andExpect(status().isCreated());
        verify(apiService).modifyUserFavoriteArtist(1L, 2L);
    }

    @Test
    public void userFavoriteArtistTopAlbums() throws Exception {
        mockMvc.perform(get("/api/user/1/favorite-artist-top-albums"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
        verify(apiService).getFavoriteArtistTopAlbums(1L);
    }
}
