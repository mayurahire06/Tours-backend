package com.mh.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mh.backend.enums.BookingStatus;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

    @Column(nullable = false)
    private Integer members;

    @Column(nullable = false)
    private LocalDate bookingDate;

    @Column(nullable = false)
    private Double totalPrice;

    private String transportation;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Traveler> travelers = new ArrayList<>();

    // Constructors
    public Booking() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Tour getTour() { return tour; }
    public void setTour(Tour tour) { this.tour = tour; }
    public Integer getMembers() { return members; }
    public void setMembers(Integer members) { this.members = members; }
    public LocalDate getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDate bookingDate) { this.bookingDate = bookingDate; }
    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }
    public String getTransportation() { return transportation; }
    public void setTransportation(String transportation) { this.transportation = transportation; }
    public List<Traveler> getTravelers() { return travelers; }
    public void setTravelers(List<Traveler> travelers) { this.travelers = travelers; }

    @PrePersist
    public void setDefaultStatus() {
        if (this.status == null) {
            this.status = BookingStatus.PENDING;
        }
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    // Helper method to add a traveler
    public void addTraveler(Traveler traveler) {
        travelers.add(traveler);
        traveler.setBooking(this); // Maintain bidirectional relationship
    }
}