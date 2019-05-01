package pl.edu.agh.placescore.model.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PlacesDTO {
    private List<PlaceDTO> places;
}
