package com.mh.backend.dto;

// This class is a Data Transfer Object (DTO) used to transfer data for creating a new category.
// It helps to decouple the API layer from the database entity layer.
public class CategoryRequest {

    private String name;

    // Default constructor
    public CategoryRequest() {
    }

    // Parameterized constructor
    public CategoryRequest(String name) {
        this.name = name;
    }

    // Getter for catName
    public String getName() {
        return name;
    }

    // Setter for catName
    public void setName(String name) {
        this.name = name;
    }
}
