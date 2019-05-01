package pl.edu.agh.placescore.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private String date;
    private String author;
    private Long rating;
    private String text;
    private String provider;
    private String url;
}
