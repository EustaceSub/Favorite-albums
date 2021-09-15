package lt.justas.demo.integration.itunes.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lt.justas.demo.model.dto.ArtistDTO;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

@AllArgsConstructor
@Slf4j
public class ItunesArtistsDataParser implements ItunesDataParser<ArtistDTO> {
    @Getter
    private final ObjectMapper objectMapper;

    public List<ArtistDTO> parseData(String responseRaw) {
        var jsonNode = parseJsonString(responseRaw);
        var resultCount = jsonNode.get("resultCount").asInt();
        if (resultCount == 0) {
            return emptyList();
        }
        var results = jsonNode.get("results");
        var artists = new ArrayList<ArtistDTO>();
        for (var artistData : results) {
            var artist = objectMapper.convertValue(artistData, ArtistDTO.class);
            artists.add(artist);
        }
        if (resultCount != artists.size()) {
            log.error("Result Count {} from itunes do not match Artist Data size {}.", resultCount, artists.size());
        }
        return artists;
    }
}
