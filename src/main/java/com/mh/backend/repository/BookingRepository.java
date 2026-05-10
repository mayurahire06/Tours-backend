package com.mh.backend.repository;

import com.mh.backend.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
    boolean existsByUserId(Long userId);

//    @Query("SELECT SUM(b.totalPrice) FROM Booking b")
//    Double getTotalRevenue();
}
