package pl.edu.agh.placescore.repository;

import java.util.Collection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.placescore.model.common.Geo;
import pl.edu.agh.placescore.model.entity.PlaceEntity;

@Repository
public interface PlacesRepository extends MongoRepository<PlaceEntity, String> {
    Collection<PlaceEntity> findAllByGeo(Geo geo);

    Collection<PlaceEntity> findAllByName(String name);
}
