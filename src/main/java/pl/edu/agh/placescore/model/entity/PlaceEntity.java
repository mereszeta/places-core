package pl.edu.agh.placescore.model.entity;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.agh.placescore.model.common.Geo;

@Getter
@Document(collection = "places")
public class PlaceEntity {

    public PlaceEntity(String name, String category, Geo geo, String phone, String description) {
        this.name = name;
        this.category = category;
        this.geo = geo;
        this.phone = phone;
        this.description = description;
        reviews = new ArrayList<>();
    }

    @Id
    private String id;
    private String name;
    private String category;
    private Geo geo;
    private String phone;
    private String description;
    private List<ReviewEntity> reviews;

    public void addReview(ReviewEntity review) {
        this.reviews.add(review);
    }
}
