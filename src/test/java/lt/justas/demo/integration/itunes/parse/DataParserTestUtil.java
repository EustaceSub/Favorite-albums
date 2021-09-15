package lt.justas.demo.integration.itunes.parse;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import java.io.IOException;

@UtilityClass
public class DataParserTestUtil {

    public static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static JsonNode readJsonFromFile(String s) throws IOException {
        var in = ItunesArtistsDataParserTest.class.getClassLoader()
                .getResourceAsStream(s);
        return MAPPER.readTree(
                in
        );
    }
}
