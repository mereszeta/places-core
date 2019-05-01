package pl.edu.agh.placescore.model.common;

import lombok.Value;

@Value
public class Address {
    String street;
    String city;
    String country;
}
