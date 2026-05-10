package com.mh.backend.dto;

import java.time.LocalDate;
import java.util.List;

public class BookingDTO {
    private Long id;
    private Long userId;
    private Long tourId;
    private Integer members;
    private LocalDate bookingDate;
    private Double totalPrice;
    private String transportation;
    private List<TravelerDTO> travelers;

    // Constructors, getters, setters as previously provided
    public BookingDTO() {}

    public BookingDTO(Long id, Long userId, Long tourId, Integer members, LocalDate bookingDate, Double totalPrice, String transportation, List<TravelerDTO> travelers) {
        this.id = id;
        this.userId = userId;
        this.tourId = tourId;
        this.members = members;
        this.bookingDate = bookingDate;
        this.totalPrice = totalPrice;
        this.transportation = transportation;
        this.travelers = travelers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    public Integer getMembers() {
        return members;
    }

    public void setMembers(Integer members) {
        this.members = members;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTransportation() {
        return transportation;
    }

    public void setTransportation(String transportation) {
        this.transportation = transportation;
    }

    public List<TravelerDTO> getTravelers() {
        return travelers;
    }

    public void setTravelers(List<TravelerDTO> travelers) {
        this.travelers = travelers;
    }
}