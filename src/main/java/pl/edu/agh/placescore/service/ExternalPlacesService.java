package pl.edu.agh.placescore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.edu.agh.placescore.model.dto.PlaceDTO;

import java.util.List;

@Service
public class ExternalPlacesService {
    private static final String EXTERNAL_API = "https://places-external.herokuapp.com";
    private final RestTemplate restTemplate;

    @Autowired
    public ExternalPlacesService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.rootUri(EXTERNAL_API).build();
    }

    public List<PlaceDTO> getPlacesByQueryParams(){
        return null;
    }
}
