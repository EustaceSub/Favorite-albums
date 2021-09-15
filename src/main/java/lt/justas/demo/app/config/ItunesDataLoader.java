package lt.justas.demo.app.config;

import lt.justas.demo.integration.itunes.fetch.ItunesArtistTopAlbumsFetcher;
import lt.justas.demo.integration.itunes.fetch.ItunesArtistsFetcher;
import lt.justas.demo.integration.itunes.parse.ItunesArtistTopAlbumsParser;
import lt.justas.demo.integration.itunes.parse.ItunesArtistsDataParser;
import lt.justas.demo.model.dto.AlbumDTO;
import lt.justas.demo.model.dto.ArtistDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItunesDataLoader {
    private final ItunesArtistsFetcher itunesArtistsFetcher;
    private final ItunesArtistsDataParser itunesArtistsDataParser;
    private final ItunesArtistTopAlbumsFetcher itunesArtistTopAlbumsFetcher;
    private final ItunesArtistTopAlbumsParser itunesArtistTopAlbumsParser;

    public ItunesDataLoader(ItunesArtistsFetcher itunesArtistsFetcher,
                            ItunesArtistsDataParser itunesArtistsDataParser,
                            ItunesArtistTopAlbumsFetcher itunesArtistTopAlbumsFetcher,
                            ItunesArtistTopAlbumsParser itunesArtistTopAlbumsParser) {
        this.itunesArtistsFetcher = itunesArtistsFetcher;
        this.itunesArtistsDataParser = itunesArtistsDataParser;
        this.itunesArtistTopAlbumsFetcher = itunesArtistTopAlbumsFetcher;
        this.itunesArtistTopAlbumsParser = itunesArtistTopAlbumsParser;
    }

    public List<ArtistDTO> loadArtistsByName(String artistName) {
        var data = itunesArtistsFetcher.fetchData(artistName);
        return itunesArtistsDataParser.parseData(data);
    }

    public List<AlbumDTO> loadTopArtistAlbums(Long artistId) {
        var data = itunesArtistTopAlbumsFetcher.fetchData(artistId);
        return itunesArtistTopAlbumsParser.parseData(data);
    }
}
