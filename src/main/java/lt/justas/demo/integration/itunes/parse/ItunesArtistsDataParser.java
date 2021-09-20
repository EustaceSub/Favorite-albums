package lt.justas.demo.integration.itunes.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lt.justas.demo.model.dto.ArtistDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

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
        JsonNode results = jsonNode.get("results");
        var artists = StreamSupport.stream(results.spliterator(), false)
                .map(artistData -> objectMapper.convertValue(artistData, ArtistDTO.class))
                .collect(toList());

        if (resultCount != artists.size()) {
            log.error("Result Count {} from itunes do not match Artist Data size {}.", resultCount, artists.size());
        }
        return artists;
    }
}
