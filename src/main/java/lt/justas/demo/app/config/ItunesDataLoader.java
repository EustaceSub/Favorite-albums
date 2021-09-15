package lt.justas.demo.app.config;

import lt.justas.demo.integration.itunes.fetch.ItunesArtistsFetcher;
import lt.justas.demo.integration.itunes.parse.ItunesArtistsDataParser;
import lt.justas.demo.model.dto.ArtistDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItunesDataLoader {
    private final ItunesArtistsFetcher itunesArtistsFetcher;
    private final ItunesArtistsDataParser itunesArtistsDataParser;

    public ItunesDataLoader(ItunesArtistsFetcher itunesArtistsFetcher, ItunesArtistsDataParser itunesArtistsDataParser) {
        this.itunesArtistsFetcher = itunesArtistsFetcher;
        this.itunesArtistsDataParser = itunesArtistsDataParser;
    }

    public List<ArtistDTO> loadArtistsByName(String artistName) {
        var data = itunesArtistsFetcher.fetchData(artistName);
        return itunesArtistsDataParser.parseData(data);
    }
}
