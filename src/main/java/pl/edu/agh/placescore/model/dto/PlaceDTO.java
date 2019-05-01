package pl.edu.agh.placescore.model.dto;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.edu.agh.placescore.model.common.Address;
import pl.edu.agh.placescore.model.common.Geo;
import pl.edu.agh.placescore.model.common.Photo;

@Getter
@RequiredArgsConstructor
public class PlaceDTO {
    private final String id;
    private final String name;
    private final String category;
    @Setter
    private Address address;
    private final Geo geo;
    private final String phone;
    private final String description;
    private final List<ReviewDTO> reviews;
    @Setter
    private List<Photo> photos;

    public void appendReviews(List<ReviewDTO> reviews) {
        this.reviews.addAll(reviews);
    }
}
