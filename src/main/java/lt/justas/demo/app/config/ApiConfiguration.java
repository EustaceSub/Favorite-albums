package lt.justas.demo.app.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.justas.demo.integration.itunes.fetch.ItunesArtistTopAlbumsFetcher;
import lt.justas.demo.integration.itunes.fetch.ItunesArtistsFetcher;
import lt.justas.demo.integration.itunes.parse.ItunesArtistTopAlbumsParser;
import lt.justas.demo.integration.itunes.parse.ItunesArtistsDataParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApiConfiguration {

    @Bean
    ItunesArtistsFetcher itunesArtistsFetcher(@Value("${demo.app.itunes.hostUrl}") String itunesUrl) {
        return new ItunesArtistsFetcher(new RestTemplate(), itunesUrl);
    }

    @Bean
    ItunesArtistTopAlbumsFetcher itunesArtistTopAlbumsFetcher(@Value("${demo.app.itunes.hostUrl}") String itunesUrl) {
        return new ItunesArtistTopAlbumsFetcher(new RestTemplate(), itunesUrl);
    }

    @Bean
    ItunesArtistsDataParser itunesArtistsDataParser(@Autowired ObjectMapper itunesArtistObjectMapper) {
        return new ItunesArtistsDataParser(itunesArtistObjectMapper);
    }

    @Bean
    ItunesArtistTopAlbumsParser itunesArtistTopAlbumsParser(@Autowired ObjectMapper itunesArtistObjectMapper) {
        return new ItunesArtistTopAlbumsParser(itunesArtistObjectMapper);
    }


    @Bean
    ObjectMapper itunesArtistObjectMapper() {
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
