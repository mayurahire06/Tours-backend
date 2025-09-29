package com.mh.backend.dto;

public class TravelerDTO {
    private Long id;
    private String name;
    private Integer age;
    private String gender;
    private Long bookingId;

    // Constructors, getters, setters as previously provided
    public TravelerDTO() { }

    public TravelerDTO(Long id, String name, Integer age, String gender, Long bookingId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.bookingId = bookingId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
}