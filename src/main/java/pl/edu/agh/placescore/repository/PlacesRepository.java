package pl.edu.agh.placescore.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.placescore.model.common.Geo;
import pl.edu.agh.placescore.model.entity.PlaceEntity;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface PlacesRepository extends MongoRepository<PlaceEntity, String> {
    Collection<PlaceEntity> findAllByGeo(Geo geo);

    Collection<PlaceEntity> findAllByName(String name);

    Collection<PlaceEntity> findAllByGeoAndNameLike(Geo geo, String name);

    Optional<PlaceEntity> findFirstByNameAndGeo(String name, Geo geo);
}
