package com.mh.backend.repository;

import com.mh.backend.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
//    List<Image> findByTourId(Long tourId);

//    List<Image> findByTour(Tour tour);
}
