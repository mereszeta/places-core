package pl.edu.agh.placescore.model.common;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Geo {
    private Double lat;
    private Double lng;
}
