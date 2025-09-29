package com.mh.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for creating and updating tours.
 * This class defines the structure of the JSON payload expected from the client.
 */

public class TourRequest {

    private String title;
    private String description;
    private double price;
//    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
//    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private int duration;
    private int capacity;
    private String source;
    private String destination;
    private String transportation;
    private double tdPrice;
    private String itinerary;
    private Long categoryId; // The ID of the category this tour belongs to

    public TourRequest() { }

    public TourRequest(String title, String description, double price, LocalDate startDate, LocalDate endDate, int duration, int capacity, String source, String destination, String transportation, double tdPrice, String itinerary, Long categoryId) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.capacity = capacity;
        this.source = source;
        this.destination = destination;
        this.transportation = transportation;
        this.tdPrice = tdPrice;
        this.itinerary = itinerary;
        this.categoryId = categoryId;
    }


    // Getters and Setters for all fields

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTransportation() {
        return transportation;
    }

    public void setTransportation(String transportation) {
        this.transportation = transportation;
    }

    public double getTdPrice() {
        return tdPrice;
    }

    public void setTdPrice(double tdPrice) {
        this.tdPrice = tdPrice;
    }

    public String getItinerary() {
        return itinerary;
    }

    public void setItinerary(String itinerary) {
        this.itinerary = itinerary;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}

