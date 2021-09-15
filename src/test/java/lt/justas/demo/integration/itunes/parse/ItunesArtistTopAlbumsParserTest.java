package lt.justas.demo.integration.itunes.parse;

import lt.justas.demo.model.dto.AlbumDTO;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static lt.justas.demo.integration.itunes.parse.DataParserTestUtil.MAPPER;
import static lt.justas.demo.integration.itunes.parse.DataParserTestUtil.readJsonFromFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ItunesArtistTopAlbumsParserTest {

    private final ItunesDataParser<AlbumDTO> itunesDataParser = new ItunesArtistTopAlbumsParser(MAPPER);

    @Test
    public void itunesNoAlbumsByArtistIdFound() throws IOException {
        var responseRaw = readJsonFromFile("lt/justas/demo/integration/itunes/artistNoAlbums.json");
        var result = itunesDataParser.parseData(responseRaw.toString());
        assertEquals(0, result.size());
    }

    @Test
    public void itunesTopAlbumsEmptyParseCorrectly() throws IOException {
        var responseRaw = readJsonFromFile("lt/justas/demo/integration/itunes/artistNoAlbums.json");
        var result = itunesDataParser.parseData(responseRaw.toString());
        assertEquals(0, result.size());
    }

    @Test
    public void itunesTopAlbumsParseCorrectly() throws IOException {
        var responseRaw = readJsonFromFile("lt/justas/demo/integration/itunes/artistTop5FavoriteAlbums.json");
        var result = itunesDataParser.parseData(responseRaw.toString());
        assertEquals(5, result.size());
        result.forEach(this::assertAlbumRequiredFieldsAreFilled);
    }

    private void assertAlbumRequiredFieldsAreFilled(AlbumDTO albumDTO) {
        assertNotNull(albumDTO.getArtistId());
        assertNotNull(albumDTO.getAmgArtistId());
        assertNotNull(albumDTO.getArtistViewUrl());

        assertNotNull(albumDTO.getCollectionId());
        assertNotNull(albumDTO.getCollectionCensoredName());
        assertNotNull(albumDTO.getCollectionName());

        assertNotNull(albumDTO.getCopyright());
        assertNotNull(albumDTO.getCountry());
        assertNotNull(albumDTO.getPrimaryGenreName());
        assertNotNull(albumDTO.getReleaseDate());
    }
}
