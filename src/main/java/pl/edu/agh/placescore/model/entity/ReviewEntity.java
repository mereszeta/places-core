package pl.edu.agh.placescore.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewEntity {
    private String date;
    private String author;
    private Long rating;
    private String text;
}
