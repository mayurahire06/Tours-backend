package com.mh.backend.service.impl;

import com.mh.backend.dto.CategoryInfoDTO;
import com.mh.backend.dto.TourRequest;
import com.mh.backend.dto.TourResponseDTO;
import com.mh.backend.entity.Category;
import com.mh.backend.entity.Image;
import com.mh.backend.entity.Tour;
import com.mh.backend.repository.CategoryRepository;
import com.mh.backend.repository.ImageRepository;
import com.mh.backend.repository.TourRepository;
import com.mh.backend.service.TourService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TourServiceImpl implements TourService {

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    private CategoryRepository categoryRepository; // Assuming you have this repository

    private final Path uploadDir;

    public TourServiceImpl() throws IOException {
        // Set the directory where images will be stored
        this.uploadDir = Paths.get("uploads"); //creates a Path object representing the relative path "uploads" in your project directory.

        // Create the folder if it doesn’t exist
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
    }

    @Transactional //if not worked then try another package of it
    @Override
    public TourResponseDTO createTour(TourRequest tourRequest, List<MultipartFile> files) throws IOException {

        // 1️⃣ Validate category
        Category existingCategory = categoryRepository.findById(tourRequest.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Category not found with ID: " + tourRequest.getCategoryId()));

        // 2️⃣ Create new Tour entity
        Tour newTour = new Tour();
        newTour.setTitle(tourRequest.getTitle());
        newTour.setDescription(tourRequest.getDescription());
        newTour.setPrice(tourRequest.getPrice());
        newTour.setStartDate(tourRequest.getStartDate());
        newTour.setEndDate(tourRequest.getEndDate());
        newTour.setDuration(tourRequest.getDuration());
        newTour.setCapacity(tourRequest.getCapacity());
        newTour.setSource(tourRequest.getSource());
        newTour.setDestination(tourRequest.getDestination());
        newTour.setTransportation(tourRequest.getTransportation());
        newTour.setTdPrice(tourRequest.getTdPrice());
        newTour.setItinerary(tourRequest.getItinerary());
        newTour.setCategory(existingCategory);

        // 3️⃣ Save tour first
        Tour savedTour = tourRepository.save(newTour);

        // 4️⃣ Handle multiple images
        List<Image> savedImages = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;

            String originalFilename = file.getOriginalFilename();
            String uniqueFilename = UUID.randomUUID() + "_" + originalFilename;
            Path filePath = this.uploadDir.resolve(uniqueFilename); //combines the directory with the filename to get the full path of the file.

            Files.copy(file.getInputStream(), filePath);

            Image image = new Image();
            image.setImageName(originalFilename);
            image.setFilePath("/uploads/" + uniqueFilename); // send URL/path for client
            image.setTour(savedTour);

            savedImages.add(image);
        }

        // Save all images
        imageRepository.saveAll(savedImages);

        // 5️⃣ Build response DTO
        TourResponseDTO response = new TourResponseDTO();
        response.setId(savedTour.getId());
        response.setTitle(savedTour.getTitle());
        response.setDescription(savedTour.getDescription());
        response.setPrice(savedTour.getPrice());
        response.setStartDate(savedTour.getStartDate());
        response.setEndDate(savedTour.getEndDate());
        response.setDuration(savedTour.getDuration());
        response.setCapacity(savedTour.getCapacity());
        response.setSource(savedTour.getSource());
        response.setDestination(savedTour.getDestination());
        response.setTransportation(savedTour.getTransportation());
        response.setTdPrice(savedTour.getTdPrice());
        response.setItinerary(savedTour.getItinerary());
        response.setImages(savedImages); // send image list

        // 6️⃣ Category info
        CategoryInfoDTO categoryInfo = new CategoryInfoDTO();
        categoryInfo.setId(existingCategory.getId());
        categoryInfo.setName(existingCategory.getName());
        response.setCategory(categoryInfo);

        return response;
    }

    //we are using in service class not in controller
    @Override
    public List<Tour> getAllTours() {
        return tourRepository.findAll();
    }

    // 🔹 Return DTO list
    @Override
    public List<TourResponseDTO> getAllToursDTO() {
        List<Tour> tours = getAllTours();
        return tours.stream()
                .map(this::toTourResponseDTO)
                .collect(Collectors.toList());
    }

    // 🔹 Convert Tour entity → DTO
    @Override
    public TourResponseDTO toTourResponseDTO(Tour tour) {
        TourResponseDTO dto = new TourResponseDTO();

        dto.setId(tour.getId());
        dto.setTitle(tour.getTitle());
        dto.setDescription(tour.getDescription());
        dto.setPrice(tour.getPrice());
        dto.setStartDate(tour.getStartDate());
        dto.setEndDate(tour.getEndDate());
        dto.setDuration(tour.getDuration());
        dto.setCapacity(tour.getCapacity());
        dto.setSource(tour.getSource());
        dto.setDestination(tour.getDestination());
        dto.setTransportation(tour.getTransportation());
        dto.setTdPrice(tour.getTdPrice());
        dto.setItinerary(tour.getItinerary());

        // 🔹 Images
        List<Image> images = tour.getImages();
        if (images != null) {
            dto.setImages(
                    images.stream()
                            .map(img -> {
                                Image i = new Image();
                                i.setId(img.getId());
                                i.setImageName(img.getImageName());
                                i.setFilePath(img.getFilePath());
                                return i;
                            }).collect(Collectors.toList())
            );
        }

        // 🔹 Category info
        Category category = tour.getCategory();
        if (category != null) {
            CategoryInfoDTO catDTO = new CategoryInfoDTO();
            catDTO.setId(category.getId());
            catDTO.setName(category.getName());
            dto.setCategory(catDTO);
        }

        return dto;
    }

    // ✅ Get tour by ID
    @Override
    public TourResponseDTO getTourById(Long id) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tour not found with ID: " + id));

        return toTourResponseDTO(tour);
    }

    // ✅ Update Tour
    @Override
    public TourResponseDTO updateTour(Long id, TourRequest tourRequest) {
        Tour existingTour = tourRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tour not found with ID: " + id));

        // Update fields
        existingTour.setTitle(tourRequest.getTitle());
        existingTour.setDescription(tourRequest.getDescription());
        existingTour.setPrice(tourRequest.getPrice());
        existingTour.setStartDate(tourRequest.getStartDate());
        existingTour.setEndDate(tourRequest.getEndDate());
        existingTour.setDuration(tourRequest.getDuration());
        existingTour.setCapacity(tourRequest.getCapacity());
        existingTour.setSource(tourRequest.getSource());
        existingTour.setDestination(tourRequest.getDestination());
        existingTour.setTransportation(tourRequest.getTransportation());
        existingTour.setTdPrice(tourRequest.getTdPrice());
        existingTour.setItinerary(tourRequest.getItinerary());

        // Update category if provided
        if (tourRequest.getCategoryId() != null) {
            Category category = categoryRepository.findById(tourRequest.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found with ID: " + tourRequest.getCategoryId()));
            existingTour.setCategory(category);
        }

        Tour updatedTour = tourRepository.save(existingTour);
        return toTourResponseDTO(updatedTour);
    }

    // ✅ Delete Tour
    @Override
    public void deleteTour(Long id) {
        Tour existingTour = tourRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tour not found with ID: " + id));

        // First delete images (if cascade is not set)
        imageRepository.deleteAll(existingTour.getImages());

        // Then delete tour
        tourRepository.delete(existingTour);
    }

    public List<TourResponseDTO> getToursByCategory(Long categoryId) {
        List<Tour> tours =  tourRepository.findByCategoryId(categoryId);
        return tours.stream()
                .map(this::toTourResponseDTO)
                .collect(Collectors.toList());
    }
}


//It is correct but we are sending unnecesary data as response
//    @Override
//    public TourResponseDTO createTour(TourRequest tourRequest, List<MultipartFile> files) throws IOException {
//
//        // 1️⃣ Validate category
//        Category existingCategory = categoryRepository.findById(tourRequest.getCategoryId())
//                .orElseThrow(() -> new EntityNotFoundException(
//                        "Category not found with ID: " + tourRequest.getCategoryId()));
//
//        // 2️⃣ Create new Tour entity (instead of fetching random one)
//        Tour newTour = new Tour();
//        newTour.setTitle(tourRequest.getTitle());
//        newTour.setDescription(tourRequest.getDescription());
//        newTour.setPrice(tourRequest.getPrice());
//        newTour.setStartDate(tourRequest.getStartDate());
//        newTour.setEndDate(tourRequest.getEndDate());
//        newTour.setDuration(tourRequest.getDuration());
//        newTour.setCapacity(tourRequest.getCapacity());
//        newTour.setDestination(tourRequest.getDestination());
//        newTour.setTransportation(tourRequest.getTransportation());
//        newTour.setTdPrice(tourRequest.getTdPrice());
//        newTour.setItinerary(tourRequest.getItinerary());
//        newTour.setCategory(existingCategory);
//
//        // 3️⃣ Save tour first to generate ID for FK in images
//        Tour savedTour = tourRepository.save(newTour);
//
//        // 4️⃣ Handle multiple images
//        List<Image> imagesToSave = new ArrayList<>();
//        for (MultipartFile file : files) {
//            if (file.isEmpty()) continue;
//
//            String originalFilename = file.getOriginalFilename();
//            String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;
//            Path filePath = this.uploadDir.resolve(uniqueFilename);
//
//            // Save file to local storage
//            Files.copy(file.getInputStream(), filePath);
//
//            Image image = new Image();
//            image.setImageName(originalFilename);
//            image.setFilePath(filePath.toString());
//            image.setTour(savedTour);
//
//            imagesToSave.add(image);
//        }
//
//        // 5️⃣ Save all images
//        List<Image> savedImages = imageRepository.saveAll(imagesToSave);
//
//        // 6️⃣ Build response DTO
//        TourResponseDTO response = new TourResponseDTO();
//        response.setId(savedTour.getId());
//        response.setTitle(savedTour.getTitle());
//        response.setDescription(savedTour.getDescription());
//        response.setPrice(savedTour.getPrice());
//        response.setStartDate(savedTour.getStartDate());
//        response.setEndDate(savedTour.getEndDate());
//        response.setDuration(savedTour.getDuration());
//        response.setCapacity(savedTour.getCapacity());
//        response.setDestination(savedTour.getDestination());
//        response.setTransportation(savedTour.getTransportation());
//        response.setTdPrice(savedTour.getTdPrice());
//        response.setItinerary(savedTour.getItinerary());
//        response.setImages(savedImages);
//
//        // 7️⃣ Category info
//        CategoryInfoDTO categoryInfo = new CategoryInfoDTO();
//        categoryInfo.setId(existingCategory.getId());
//        categoryInfo.setName(existingCategory.getName());
//        response.setCategory(categoryInfo);
//
//        return response;
//    }