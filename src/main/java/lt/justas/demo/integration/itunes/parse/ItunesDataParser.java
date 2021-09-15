package lt.justas.demo.integration.itunes.parse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collection;

public interface ItunesDataParser<T> {

    ObjectMapper getObjectMapper();

    Collection<T> parseData(String rawData);

    default JsonNode parseJsonString(String jsonString) {
        try {
            return getObjectMapper().readTree(jsonString);
        } catch (JsonProcessingException e) {
            throw new ItunesDataParseException(e);
        }
    }
}
