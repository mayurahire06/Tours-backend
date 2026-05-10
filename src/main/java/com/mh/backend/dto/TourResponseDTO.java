package com.mh.backend.dto;

import com.mh.backend.entity.Image;
import com.mh.backend.entity.Tour;

import java.time.LocalDate;
import java.util.List;

public class TourResponseDTO {
    private Long id;
    private String title;
    private String description;
    private double price;
    private LocalDate startDate;
    private LocalDate endDate;
    private int duration;
    private int capacity;
    private String source;
    private String destination;
    private String transportation;
    private double tdPrice;
    private String itinerary;
    private List<Image> images;
    private CategoryInfoDTO category; // Use the simple DTO here

    public TourResponseDTO() { }

    // A handy constructor to convert an Entity to a DTO
    public TourResponseDTO(Tour tour) {
        this.id = tour.getId();
        this.title = tour.getTitle();
        this.description = tour.getDescription();
        this.price = tour.getPrice();
        this.startDate = tour.getStartDate();
        this.endDate = tour.getEndDate();
        this.duration = tour.getDuration();
        this.capacity = tour.getCapacity();
        this.source = tour.getSource();
        this.destination = tour.getDestination();
        this.transportation = tour.getTransportation();
        this.tdPrice = tour.getTdPrice();
        this.itinerary = tour.getItinerary();
        this.images = tour.getImages();
        if (tour.getCategory() != null) {
            this.category = new CategoryInfoDTO(tour.getCategory().getId(), tour.getCategory().getName());
        }
    }

    // Getters and Setters for all fields...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getTransportation() { return transportation; }
    public void setTransportation(String transportation) { this.transportation = transportation; }

    public double getTdPrice() { return tdPrice; }
    public void setTdPrice(double tdPrice) { this.tdPrice = tdPrice; }

    public String getItinerary() { return itinerary; }
    public void setItinerary(String itinerary) { this.itinerary = itinerary; }

    public List<Image> getImages() { return images; }
    public void setImages(List<Image> images) { this.images = images; }

    public CategoryInfoDTO getCategory() { return category; }
    public void setCategory(CategoryInfoDTO category) { this.category = category; }
}
