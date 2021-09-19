package lt.justas.demo.app.controller;

import lt.justas.demo.app.service.ApiService;
import lt.justas.demo.model.dto.AlbumDTO;
import lt.justas.demo.model.dto.ArtistDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<ArtistDTO>> search(@RequestParam("artistName") String artistName) {
        return ResponseEntity.ok(apiService.searchArtistsByName(artistName));
    }

    @PostMapping("/user/favorite/artist")
    @ResponseStatus(CREATED)
    public void setFavoriteArtist(@RequestParam Long userId, @RequestParam Long artistId) {
        apiService.modifyUserFavoriteArtist(userId, artistId);
    }

    @GetMapping("/user/{userId}/favorite-artist-top-albums")
    public ResponseEntity<Collection<AlbumDTO>> getFavoriteArtistTopAlbums(@PathVariable Long userId) {
        return ResponseEntity.ok(apiService.getFavoriteArtistTopAlbums(userId));
    }
}
