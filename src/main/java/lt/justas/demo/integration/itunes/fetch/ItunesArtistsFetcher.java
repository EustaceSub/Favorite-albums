package lt.justas.demo.integration.itunes.fetch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Slf4j
public class ItunesArtistsFetcher {

    private final RestTemplate restTemplate;
    private final String itunesHost;
    private final String entity = "allArtist"; // Support allArtists now only;
    private final int limitResults = 200;


    public String fetchData(String artistName) {
        String url = buildItunesArtistsRequest(artistName);
        log.info("Fetching data from {}", url);
        return restTemplate.getForObject(url, String.class);
    }

    private String buildItunesArtistsRequest(String artistName) {
        return UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(itunesHost)
                .pathSegment("search")
                .queryParam("term", artistName)
                .queryParam("entity", entity)
                .queryParam("limit", limitResults)
                .build()
                .toUriString();
    }
}
