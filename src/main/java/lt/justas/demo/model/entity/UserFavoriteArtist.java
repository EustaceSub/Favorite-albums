package lt.justas.demo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Data
@Entity
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserFavoriteArtist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long amgArtistId;

    public UserFavoriteArtist(Long userId) {
        this.userId = userId;
    }
}
