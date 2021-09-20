package lt.justas.demo.integration.itunes.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lt.justas.demo.model.dto.AlbumDTO;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@AllArgsConstructor
public class ItunesArtistTopAlbumsParser implements ItunesDataParser<AlbumDTO> {

    @Getter
    private final ObjectMapper objectMapper;

    @Override
    public List<AlbumDTO> parseData(String rawData) {
        var jsonNode = parseJsonString(rawData);
        var results = jsonNode.get("results");

        Predicate<JsonNode> isCollection = wrapperType -> ofNullable(wrapperType)
                .map(data -> data.get("wrapperType"))
                .map(w -> "collection".equals(w.asText()))
                .orElse(false);

        return StreamSupport.stream(results.spliterator(), false)
                .filter(isCollection)
                .map(c -> objectMapper.convertValue(c, AlbumDTO.class))
                .collect(toList());
    }
}
