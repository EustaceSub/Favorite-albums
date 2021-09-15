package lt.justas.demo.integration.itunes.parse;

import com.fasterxml.jackson.databind.JsonNode;
import lt.justas.demo.model.dto.ArtistDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static lt.justas.demo.integration.itunes.parse.DataParserTestUtil.MAPPER;
import static lt.justas.demo.integration.itunes.parse.DataParserTestUtil.readJsonFromFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItunesArtistsDataParserTest {

    private final ItunesDataParser<ArtistDTO> itunesArtistsDataParser = new ItunesArtistsDataParser(MAPPER);

    @Test
    public void itunesNoArtistDataFoundParse() throws IOException {
        JsonNode responseRaw = readJsonFromFile("lt/justas/demo/integration/itunes/noArtistData.json");
        var result = itunesArtistsDataParser.parseData(responseRaw.toString());
        assertEquals(0, result.size());
    }

    @Test
    public void itunesArtistDataParseCorrectly() throws IOException {
        JsonNode responseRaw = readJsonFromFile("lt/justas/demo/integration/itunes/artistsData.json");
        var result = itunesArtistsDataParser.parseData(responseRaw.toString());
        assertEquals(50, result.size());
    }

    @Test
    public void malformedArtistData() {
        Assertions.assertThrows(
                ItunesDataParseException.class,
                () -> itunesArtistsDataParser.parseData("No data")
        );
    }
}