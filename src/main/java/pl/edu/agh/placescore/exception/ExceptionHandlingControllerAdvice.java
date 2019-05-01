package pl.edu.agh.placescore.exception;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ControllerAdvice
@EnableWebMvc
@Slf4j
public class ExceptionHandlingControllerAdvice {

    @ExceptionHandler(NoSuchPlaceException.class)
    public ResponseEntity handleUnauthorizedException(HttpServletRequest req, NoSuchPlaceException ex) {
        return ResponseEntity.badRequest().body(ex.getTitle());
    }
}
