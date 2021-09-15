package lt.justas.demo.app.exception;

public class UserNoFavoriteArtistException extends RuntimeException {
    public UserNoFavoriteArtistException(Long userId) {
        super(new RuntimeException("User " + userId + " does not have favorite artist"));
    }
}
