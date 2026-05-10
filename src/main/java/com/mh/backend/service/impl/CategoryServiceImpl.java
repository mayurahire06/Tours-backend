package com.mh.backend.service.impl;

import com.mh.backend.dto.CategoryRequest;
import com.mh.backend.entity.Category;
import com.mh.backend.exception.ResourceNotFoundException;
import com.mh.backend.repository.CategoryRepository;
import com.mh.backend.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * Constructor-based dependency injection for the CategoryRepository.
     * @param categoryRepository The repository for database operations on Category entities.
     */
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Creates and saves a new Category entity based on the provided request data.
     *
     * @param categoryRequest The DTO containing the data for the new category.
     * @return The saved Category entity, including its generated ID.
     */
    @Override
    public Category createCategory(CategoryRequest categoryRequest) {
        // Create a new Category entity from the request DTO
        Category category = new Category();
        category.setName(categoryRequest.getName());

        // Save the new category to the database using the repository
        return categoryRepository.save(category);
    }

    /**
     * Retrieves all categories from the database.
     *
     * @return A list of all Category entities.
     */
    @Override
    public List<Category> getAllCategories() {

        return categoryRepository.findAll();
    }

    /**
     * Retrieves a single category by its ID.
     *
     * @param id The ID of the category to retrieve.
     * @return An Optional containing the found Category, or an empty Optional if not found.
     */
    @Override
    public Optional<Category> getCategoryById(Long id) {

        return categoryRepository.findById(id);
    }

    /**
     * Updates an existing category.
     *
     * @param id The ID of the category to update.
     * @param categoryRequest The request object containing the updated details.
     * @return The updated Category entity.
     * @throws ResourceNotFoundException if no category is found with the given ID.
     */
    @Override
    public Category updateCategory(Long id, CategoryRequest categoryRequest) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        existingCategory.setName(categoryRequest.getName());
        return categoryRepository.save(existingCategory);
    }

    /**
     * Deletes a category by its ID.
     *
     * @param id The ID of the category to delete.
     * @throws ResourceNotFoundException if no category is found with the given ID.
     */
    @Override
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }
}

