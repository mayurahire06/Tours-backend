package com.mh.backend.repository;

import com.mh.backend.entity.Feedback;
import com.mh.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByUser(User user);
}
