package lt.justas.demo.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArtistDTO {

    private Integer artistId;

    private Integer amgArtistId;

    private String artistName;

    private String artistLinkUrl;

    private String primaryGenreName;

    private String primaryGenreId;
}
