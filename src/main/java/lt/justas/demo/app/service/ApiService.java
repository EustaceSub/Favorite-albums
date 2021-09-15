package lt.justas.demo.app.service;

import lt.justas.demo.app.config.ItunesDataLoader;
import lt.justas.demo.model.dto.ArtistDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiService {
    private final ItunesDataLoader itunesDataLoader;

    public ApiService(ItunesDataLoader itunesDataLoader) {
        this.itunesDataLoader = itunesDataLoader;
    }

    public List<ArtistDTO> searchArtistsByName(String artistName) {
        return itunesDataLoader.loadArtistsByName(artistName);
    }
}
