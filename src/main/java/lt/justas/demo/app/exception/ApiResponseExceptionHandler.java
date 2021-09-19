package lt.justas.demo.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApiResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNoFavoriteArtistException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNoFavoriteArtistException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}