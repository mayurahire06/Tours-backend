package com.mh.backend.service;

import com.mh.backend.dto.CategoryRequest;
import com.mh.backend.entity.Category;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing categories.
 * Defines the contract for category-related business operations.
 */
public interface CategoryService {

    /**
     * Creates a new category.
     *
     * @param categoryRequest The request object containing category details.
     * @return The created Category entity.
     */
    Category createCategory(CategoryRequest categoryRequest);

    /**
     * Retrieves all categories.
     *
     * @return A list of all Category entities.
     */
    List<Category> getAllCategories();

    /**
     * Retrieves a single category by its ID.
     *
     * @param id The ID of the category to retrieve.
     * @return An Optional containing the found Category, or an empty Optional if not found.
     */
    Optional<Category> getCategoryById(Long id);

    /**
     * Updates an existing category.
     *
     * @param id The ID of the category to update.
     * @param categoryRequest The request object containing the updated details.
     * @return The updated Category entity.
     */
    Category updateCategory(Long id, CategoryRequest categoryRequest);

    /**
     * Deletes a category by its ID.
     *
     * @param id The ID of the category to delete.
     */
    void deleteCategory(Long id);

}

