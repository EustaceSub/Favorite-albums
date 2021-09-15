package lt.justas.demo.integration.itunes.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lt.justas.demo.model.dto.AlbumDTO;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
public class ItunesArtistTopAlbumsParser implements ItunesDataParser<AlbumDTO> {

    @Getter
    private final ObjectMapper objectMapper;

    @Override
    public Collection<AlbumDTO> parseData(String rawData) {
        var jsonNode = parseJsonString(rawData);

        var albums = new ArrayList<AlbumDTO>();
        for (JsonNode albumData : jsonNode.get("results")) {
            var wrapperType = albumData.get("wrapperType");
            if (wrapperType == null || !"collection".equals(wrapperType.asText())) {
                continue;
            }
            var albumDTO = objectMapper.convertValue(albumData, AlbumDTO.class);
            albums.add(albumDTO);
        }
        return albums;
    }
}
