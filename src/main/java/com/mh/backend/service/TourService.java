package com.mh.backend.service;

import com.mh.backend.dto.TourRequest;
import com.mh.backend.dto.TourResponseDTO;
import com.mh.backend.entity.Tour;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TourService {
    TourResponseDTO createTour(TourRequest tourRequest, List<MultipartFile> images) throws IOException;
    List<Tour> getAllTours();
    List<TourResponseDTO> getAllToursDTO();
    TourResponseDTO toTourResponseDTO(Tour tour);
    TourResponseDTO getTourById(Long id);
    TourResponseDTO updateTour(Long id, TourRequest tourRequest);
    void deleteTour(Long id);
    public List<TourResponseDTO> getToursByCategory(Long categoryId);
}




