package com.mh.backend.repository;

import com.mh.backend.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Tour entity.
 * This interface handles all database operations for tours.
 */
@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
    List<Tour> findByCategoryId(Long categoryId);
}

