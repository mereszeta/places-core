package pl.edu.agh.placescore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
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
import java.util.stream.Stream;

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
        List<PlaceDTO> placesDTOS = this.externalPlacesService.getPlacesByLatAndLng(lat, lng);
        return new PlacesDTO(Stream.concat(placesDTOS.stream().map(this::getPlaceDTO),
                this.placesRepository.findAllByAndPositionNear(new Point(lat, lng), new Distance(1, Metrics.KILOMETERS)).stream()
                        .filter(entity -> placesDTOS
                                .stream()
                                .noneMatch(dto -> dto.getName().equals(entity.getName()) && dto.getGeo().equals(new Geo(entity.getPosition()[0], entity.getPosition()[1]))))
                        .map(entity -> this.placeConverter.fromEntity(entity)))
                .collect(Collectors.toList()));
    }

    private PlaceDTO getPlaceDTO(PlaceDTO placeDto) {
        Optional<PlaceEntity> placeEntityOptional = this.placesRepository.findFirstByNameAndPositionNear(placeDto.getName(),
                new Point(placeDto.getGeo().getLat(), placeDto.getGeo().getLng()), new Distance(1, Metrics.KILOMETERS));
        PlaceDTO newPlaceDTO = placeEntityOptional.isPresent() ?
                this.placeConverter.fromEntity(placeEntityOptional.get()) :
                this.placeConverter.fromEntity(this.placesRepository.save(this.placeConverter.fromDTO(placeDto)));
        newPlaceDTO.appendReviews(placeDto.getReviews());
        newPlaceDTO.setPhotos(placeDto.getPhotos());
        newPlaceDTO.setAddress(placeDto.getAddress());
        return newPlaceDTO;
    }

    public PlacesDTO getPlacesByLatLngAndName(Double lat, Double lng, String name) {
        List<PlaceDTO> placesDTOS = this.externalPlacesService.getPlacesByQueryParams(lat, lng, name);
        return new PlacesDTO(Stream.concat(placesDTOS.stream().map(this::getPlaceDTO),
                this.placesRepository.findAllByAndPositionNear(new Point(lat, lng), new Distance(1, Metrics.KILOMETERS)).stream()
                        .filter(entity -> placesDTOS
                                .stream()
                                .noneMatch(dto -> dto.getName().equals(entity.getName()) && dto.getGeo().equals(new Geo(entity.getPosition()[0], entity.getPosition()[1])))
                                && entity.getName().matches("(?i:.*" + name + ".*)"))
                        .map(entity -> this.placeConverter.fromEntity(entity)))
                .collect(Collectors.toList()));
    }

    public void addReviewToPlace(String id, ReviewDTO review) throws NoSuchPlaceException {
        Optional<PlaceEntity> placeEntityOptional = this.placesRepository.findById(id);
        PlaceEntity placeEntity = placeEntityOptional.orElseThrow(() -> new NoSuchPlaceException(id));
        placeEntity.addReview(this.reviewConverter.fromDTO(review));
        this.placesRepository.save(placeEntity);
    }
}
