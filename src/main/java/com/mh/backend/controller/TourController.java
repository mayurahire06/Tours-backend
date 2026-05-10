package com.mh.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mh.backend.dto.TourRequest;
import com.mh.backend.dto.TourResponseDTO;
import com.mh.backend.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@CrossOrigin(origins = "http://localhost:5173") no need because of WebConfig
@RequestMapping("/api/tours")
public class TourController {

    @Autowired
    private TourService tourService;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Map<String, Object>> createTour(
            @RequestPart("tourRequest") String tourRequestJson,
            @RequestPart("images") List<MultipartFile> images) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // important for LocalDate actually no need because ObjectMapper internally also uses but we are facing that issue so that why we used.
        TourRequest tourRequest = mapper.readValue(tourRequestJson, TourRequest.class);
        TourResponseDTO tourResponseDTO = tourService.createTour(tourRequest, images);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Tour added successfully");
        response.put("tour", tourResponseDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

//    @GetMapping
//    public ResponseEntity<List<TourResponseDTO>> getAllTours() {
//        List<TourResponseDTO> response = tourService.getAllToursDTO();
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @GetMapping
    public ResponseEntity<List<TourResponseDTO>> getTours(@RequestParam(required = false) Long catId) {
        List<TourResponseDTO> response;
        if (catId != null) {
            response = tourService.getToursByCategory(catId);
        } else {
            response = tourService.getAllToursDTO();
        }
        return ResponseEntity.ok(response);
    }


    // ✅ Get tour by ID
    @GetMapping("/{id}")
    public ResponseEntity<TourResponseDTO> getTourById(@PathVariable Long id) {
        TourResponseDTO response = tourService.getTourById(id);
        return ResponseEntity.ok(response);
    }

    // ✅ Update Tour
    @PutMapping("/{id}")
    public ResponseEntity<TourResponseDTO> updateTour(
            @PathVariable Long id,
            @RequestBody TourRequest tourRequest) {

        TourResponseDTO updatedTour = tourService.updateTour(id, tourRequest);
        return ResponseEntity.ok(updatedTour);
    }

    // ✅ Delete Tour
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable Long id) {
        tourService.deleteTour(id);
        return ResponseEntity.noContent().build();
    }


    //this method handles the exception
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteTour(@PathVariable Long id) {
//        try {
//            tourService.deleteTour(id);
//            return ResponseEntity.noContent().build();
//        } catch (EntityNotFoundException ex) {
//            return ResponseEntity
//                    .status(HttpStatus.NOT_FOUND)
//                    .body(ex.getMessage());
//        }
//    }

    //this will be the json
//    {
//        "status": 404,
//            "error": "Tour not found",
//            "message": "Tour not found with ID: 5"
//    }


    // Get all tours or filter by category
//    @GetMapping
//    public ResponseEntity<List<TourResponseDTO>> getTours(@RequestParam(required = false) Long catId) {
//        List<TourResponseDTO> response;
//        if (catId != null) {
//            response = tourService.getToursByCategory(catId);
//        } else {
//            response = tourService.getAllToursDTO();
//        }
//        return ResponseEntity.ok(response);
//    }

}


//Getting issue because we have bidirectional relationships (Tour → Image → Tour) or lazy-loaded fields. This often causes:
//Infinite recursion during JSON serialization
//LazyInitializationException if you use FetchType.LAZY outside a transaction
//    @GetMapping
//    public ResponseEntity<List<Tour>> getAllTours() {
//        List<Tour> tours = tourService.getAllTours();
//        return new ResponseEntity<>(tours, HttpStatus.OK);
//    }