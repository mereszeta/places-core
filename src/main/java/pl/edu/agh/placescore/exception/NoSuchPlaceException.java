package pl.edu.agh.placescore.exception;

import lombok.Getter;

@Getter
public class NoSuchPlaceException extends RuntimeException {
    private final String title;

    public NoSuchPlaceException(String id) {
        this.title = "No place with id: " + id;
    }
}
