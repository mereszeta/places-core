package pl.edu.agh.placescore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.placescore.converter.NewPlaceConverter;
import pl.edu.agh.placescore.converter.PlaceConverter;
import pl.edu.agh.placescore.converter.ReviewConverter;
import pl.edu.agh.placescore.exception.NoSuchPlaceException;
import pl.edu.agh.placescore.model.common.Geo;
import pl.edu.agh.placescore.model.dto.NewPlaceDTO;
import pl.edu.agh.placescore.model.dto.PlaceDTO;
import pl.edu.agh.placescore.model.dto.PlacesDTO;
import pl.edu.agh.placescore.model.dto.ReviewDTO;
import pl.edu.agh.placescore.model.entity.PlaceEntity;
import pl.edu.agh.placescore.repository.PlacesRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class PlacesService {

    private PlacesRepository placesRepository;
    private NewPlaceConverter newPlaceConverter;
    private PlaceConverter placeConverter;
    private ReviewConverter reviewConverter;
    private ExternalPlacesService externalPlacesService;

    @Autowired
    public PlacesService(PlacesRepository placesRepository, PlaceConverter placeConverter,
                         NewPlaceConverter newPlaceConverter, ReviewConverter reviewConverter, ExternalPlacesService externalPlacesService) {
        this.placesRepository = placesRepository;
        this.newPlaceConverter = newPlaceConverter;
        this.placeConverter = placeConverter;
        this.reviewConverter = reviewConverter;
        this.externalPlacesService = externalPlacesService;
    }

    public void addPlace(NewPlaceDTO newPlaceDTO) {
        this.placesRepository.save(newPlaceConverter.fromDTO(newPlaceDTO));
    }

    public PlacesDTO getAllPlaces() {
        return new PlacesDTO(
                this.placesRepository.findAll().stream().map(entity -> this.placeConverter.fromEntity(entity))
                        .collect(
                                toList()));
    }

    public PlacesDTO getPlacesByName(String name) {
        return new PlacesDTO(
                this.placesRepository.findAllByName(name).stream().map(entity -> this.placeConverter.fromEntity(entity))
                        .collect(
                                toList()));
    }

    public PlacesDTO getPlacesByLatAndLng(Double lat, Double lng) {
        return new PlacesDTO(
                this.placesRepository.findAllByGeo(new Geo(lat, lng)).stream()
                        .map(entity -> this.placeConverter.fromEntity(entity))
                        .collect(
                                toList()));
    }

    public PlacesDTO getPlacesByLatLngAndName(Double lat, Double lng, String name) {
        List<PlaceDTO> placesDTOS = this.externalPlacesService.getPlacesByQueryParams(lat, lng, name);
        return new PlacesDTO(placesDTOS.stream().map(placeDto -> {
            Optional<PlaceEntity> placeEntityOptional = this.placesRepository.findFirstByNameAndGeo(placeDto.getName(), placeDto.getGeo());
            PlaceDTO newPlaceDTO = placeEntityOptional.isPresent() ?
                    this.placeConverter.fromEntity(placeEntityOptional.get()) :
                    this.placeConverter.fromEntity(this.placesRepository.save(this.placeConverter.fromDTO(placeDto)));
            newPlaceDTO.appendReviews(placeDto.getReviews());
            newPlaceDTO.setPhotos(placeDto.getPhotos());
            newPlaceDTO.setAddress(placeDto.getAddress());
            return newPlaceDTO;
        }).collect(Collectors.toList()));
    }

    public void addReviewToPlace(String id, ReviewDTO review) throws NoSuchPlaceException {
        Optional<PlaceEntity> placeEntityOptional = this.placesRepository.findById(id);
        PlaceEntity placeEntity = placeEntityOptional.orElseThrow(() -> new NoSuchPlaceException(id));
        placeEntity.addReview(this.reviewConverter.fromDTO(review));
        this.placesRepository.save(placeEntity);
    }
}
