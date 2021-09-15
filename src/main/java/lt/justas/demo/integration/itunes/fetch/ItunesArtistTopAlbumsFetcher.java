package lt.justas.demo.integration.itunes.fetch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Slf4j
public class ItunesArtistTopAlbumsFetcher {
    private final RestTemplate restTemplate;
    private final String itunesHost;
    private final int limitResults = 5;
    private final String entity = "album";


    public String fetchData(Long artistId) {
        String url = buildItunesArtistsRequest(artistId);
        log.info("Fetching data from {}", url);
        return restTemplate.getForObject(url, String.class);
    }

    private String buildItunesArtistsRequest(Long artistId) {
        return UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(itunesHost)
                .pathSegment("lookup")
                .queryParam("amgArtistId", artistId)
                .queryParam("entity", entity)
                .queryParam("limit", limitResults)
                .build()
                .toUriString();
    }
}
