package com.mh.backend.repository;

import com.mh.backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The CategoryRepository interface provides the mechanism for storage, retrieval,
 * and search behavior for Category objects. It extends JpaRepository, which
 * provides JPA-related methods like save(), findOne(), findAll(), count(), delete(), etc.
 * out of the box.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Spring Data JPA will automatically implement this interface at runtime.

    // Optional<Category> findByCatName(String catName);
}
