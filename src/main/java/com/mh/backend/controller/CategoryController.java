package com.mh.backend.controller;

import com.mh.backend.dto.CategoryRequest;
import com.mh.backend.entity.Category;
import com.mh.backend.exception.ResourceNotFoundException;
import com.mh.backend.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The REST controller for managing categories.
 * This class exposes endpoints for category-related operations.
 */
@RestController
//@CrossOrigin(origins = "http://localhost:5173") not need of because of WebConfig
@RequestMapping("/api/categories") // Base path for all endpoints in this controller
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Constructor for dependency injection of the CategoryService.
     * @param categoryService The service that handles business logic for categories.
     */
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Endpoint to create a new category.
     * It handles POST requests to /api/categories.
     * The request body should contain a JSON object with a "catName" field.
     *
     * @param categoryRequest The category data from the request body.
     * @return A ResponseEntity containing the created category and an HTTP status of 201 (Created).
     */
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryRequest categoryRequest) {
        Category createdCategory = categoryService.createCategory(categoryRequest);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    /**
     * Endpoint to retrieve all categories.
     * It handles GET requests to /api/categories.
     *
     * @return A ResponseEntity containing a list of all categories and an HTTP status of 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    /**
     * Endpoint to retrieve a single category by its ID.
     * It handles GET requests to /api/categories/{id}.
     *
     * @param id The ID of the category, passed in the URL path.
     * @return A ResponseEntity containing the found category or a 404 Not Found status if it doesn't exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return ResponseEntity.ok(category);
    }

    /**
     * Endpoint to update an existing category.
     * It handles PUT requests to /api/categories/{id}.
     *
     * @param id The ID of the category to update.
     * @param categoryRequest The updated category data from the request body.
     * @return A ResponseEntity containing the updated category.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        Category updatedCategory = categoryService.updateCategory(id, categoryRequest);
        return ResponseEntity.ok(updatedCategory);
    }

    /**
     * Endpoint to delete a category.
     * It handles DELETE requests to /api/categories/{id}.
     *
     * @param id The ID of the category to delete.
     * @return A ResponseEntity with a 204 No Content status indicating successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}

