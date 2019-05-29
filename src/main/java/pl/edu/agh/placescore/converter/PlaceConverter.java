package pl.edu.agh.placescore.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.placescore.model.dto.PlaceDTO;
import pl.edu.agh.placescore.model.entity.PlaceEntity;

import static java.util.stream.Collectors.toList;

@Component
public class PlaceConverter implements BaseConverter<PlaceEntity, PlaceDTO> {

    private ReviewConverter reviewConverter;

    @Autowired
    public PlaceConverter(ReviewConverter reviewConverter) {
        this.reviewConverter = reviewConverter;
    }

    @Override
    public PlaceEntity fromDTO(PlaceDTO dto) {
        return new PlaceEntity(dto.getName(), dto.getCategory(), dto.getGeo(), dto.getPhone(), dto.getDescription());
    }

    @Override
    public PlaceDTO fromEntity(PlaceEntity entity) {
        return new PlaceDTO(entity.getId(), entity.getName(), entity.getCategory(), entity.getGeo(), entity.getPhone(),
                entity.getDescription(),
                entity.getReviews().stream().map(reviewEntity -> this.reviewConverter.fromEntity(reviewEntity))
                        .collect(toList()));
    }
}
