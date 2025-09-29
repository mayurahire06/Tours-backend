package com.mh.backend.dto;

/**
 * A simple DTO to represent basic category information in API responses.
 */
public class CategoryInfoDTO {
    private Long id;
    private String name;

    public CategoryInfoDTO() { }

    // Constructors, Getters, and Setters
    public CategoryInfoDTO(Long id, String name) {
        this.id = id;
        this.name = name;
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
}
