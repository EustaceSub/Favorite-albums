package lt.justas.demo.model.dto;

import lombok.Data;

@Data
public class AlbumDTO {
    private Long artistId;

    private Long amgArtistId;

    private Long collectionId;

    private String collectionName;

    private String collectionCensoredName;

    private String artistViewUrl;

    private String collectionViewUrl;

    private String collectionPrice;

    private String trackCount;

    private String copyright;

    private String country;

    private String currency;

    private String releaseDate;

    private String primaryGenreName;
}
