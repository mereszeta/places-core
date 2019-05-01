package pl.edu.agh.placescore.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edu.agh.placescore.model.common.Geo;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class NewPlaceDTO {
    private String name;
    private String category;
    private Geo geo;
    private String phone;
    private String description;
}
