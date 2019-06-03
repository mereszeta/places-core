package pl.edu.agh.placescore.repository;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.placescore.model.entity.PlaceEntity;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface PlacesRepository extends MongoRepository<PlaceEntity, String> {

    Collection<PlaceEntity> findAllByName(String name);

    Collection<PlaceEntity> findAllByAndPositionNear(Point position, Distance distance);

    Optional<PlaceEntity> findFirstByNameAndPosition(String name, Point position);
}
