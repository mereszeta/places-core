package pl.edu.agh.placescore.converter;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.agh.placescore.model.dto.NewPlaceDTO;
import pl.edu.agh.placescore.model.entity.PlaceEntity;

@NoArgsConstructor
@Component
public class NewPlaceConverter implements BaseConverter<PlaceEntity, NewPlaceDTO> {

    @Override
    public PlaceEntity fromDTO(NewPlaceDTO dto) {
        return new PlaceEntity(dto.getName(), dto.getCategory(), dto.getGeo(), dto.getPhone(), dto.getDescription());
    }

    @Override
    public NewPlaceDTO fromEntity(PlaceEntity entity) {
        return null;
    }
}
