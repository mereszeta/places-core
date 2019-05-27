package pl.edu.agh.placescore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.placescore.model.dto.NewPlaceDTO;
import pl.edu.agh.placescore.model.dto.PlacesDTO;
import pl.edu.agh.placescore.model.dto.ReviewDTO;
import pl.edu.agh.placescore.service.PlacesService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/places")
@CrossOrigin(origins = {"https://leskiw77.github.io/places_frontend"})
public class PlacesController {
    private PlacesService placesService;

    @Autowired
    public PlacesController(PlacesService placesService) {
        this.placesService = placesService;
    }

    @PostMapping
    public void addPlace(@RequestBody @Valid NewPlaceDTO newPlaceDTO) {
        this.placesService.addPlace(newPlaceDTO);
    }

    @GetMapping(params = {"!lat", "!lng"})
    public ResponseEntity<PlacesDTO> getPlacesByName(@RequestParam String name) {
        return ResponseEntity.ok(this.placesService.getPlacesByName(name));
    }

    @GetMapping(params = {"!name"})
    public ResponseEntity<PlacesDTO> getPlacesByLatAndLng(@RequestParam Long lat, @RequestParam Long lng) {
        return ResponseEntity.ok(this.placesService.getPlacesByLatAndLng(lat, lng));
    }

    @GetMapping(params = {"!lat", "!lng", "!name"})
    public ResponseEntity<PlacesDTO> getPlaces() {
        return ResponseEntity.ok(this.placesService.getAllPlaces());
    }

    @PostMapping(path = "/{id}/reviews")
    public void addReviewToPlace(@PathVariable String id, @RequestBody @Valid ReviewDTO reviewDTO) {
        this.placesService.addReviewToPlace(id, reviewDTO);
    }
}
