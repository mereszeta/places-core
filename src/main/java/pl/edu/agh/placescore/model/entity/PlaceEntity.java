package pl.edu.agh.placescore.model.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.agh.placescore.model.common.Geo;

import java.util.ArrayList;
import java.util.List;

@Getter
@Document(collection = "places")
public class PlaceEntity {

    public PlaceEntity(String name, String category, Geo geo, String phone, String description) {
        this.name = name;
        this.category = category;
        this.position = new double[]{geo.getLat(), geo.getLng()};
        this.phone = phone;
        this.description = description;
        reviews = new ArrayList<>();
    }
    @PersistenceConstructor
    public PlaceEntity(String name, String category, double[] position, String phone, String description, List<ReviewEntity> reviews) {
        super();
        this.name = name;
        this.category = category;
        this.position = position;
        this.phone = phone;
        this.description = description;
        this.reviews = reviews;
    }

    public PlaceEntity() {
    }

    @Id
    private String id;
    @TextIndexed
    private String name;
    private String category;
    @GeoSpatialIndexed
    private double[] position;
    private String phone;
    private String description;
    private List<ReviewEntity> reviews;

    public void addReview(ReviewEntity review) {
        this.reviews.add(review);
    }
}
