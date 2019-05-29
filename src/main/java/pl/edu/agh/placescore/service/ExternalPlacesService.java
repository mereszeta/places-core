package pl.edu.agh.placescore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.edu.agh.placescore.model.dto.PlaceDTO;

import java.util.List;

@Service
public class ExternalPlacesService {
    private final RestTemplate restTemplate;

    @Autowired
    public ExternalPlacesService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<PlaceDTO> getPlacesByQueryParams(Double lat, Double lng, String name) {
        return restTemplate.exchange("https://places-external.herokuapp.com/v1/places?query={name}&lat={lat}&lng={lng}", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<PlaceDTO>>() {
                }, name, lat, lng).getBody();
    }
}
