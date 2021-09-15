package lt.justas.demo.app.controller;

import lt.justas.demo.app.service.ApiService;
import lt.justas.demo.model.dto.ArtistDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
