package com.mh.backend.dto;

import java.time.LocalDate;

public class BookingRequest {
    private Long userId;
    private Integer members;
    private LocalDate bookingDate;

    // getters and setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Integer getMembers() { return members; }
    public void setMembers(Integer members) { this.members = members; }

    public LocalDate getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDate bookingDate) { this.bookingDate = bookingDate; }
}

