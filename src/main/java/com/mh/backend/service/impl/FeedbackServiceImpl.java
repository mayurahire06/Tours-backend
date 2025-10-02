package com.mh.backend.service.impl;

import com.mh.backend.entity.Feedback;
import com.mh.backend.entity.User;
import com.mh.backend.exception.ResourceNotFoundException;
import com.mh.backend.repository.BookingRepository;
import com.mh.backend.repository.FeedbackRepository;
import com.mh.backend.repository.UserRepository;
import com.mh.backend.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, UserRepository userRepository, BookingRepository bookingRepository) {
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    @Transactional
    public Feedback createFeedback(Long userId, String feedCat, String feedComment) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        // ✅ Check if user has a confirmed booking
        boolean hasBooking = bookingRepository.existsByUserId(userId);
        if (!hasBooking) {
            throw new IllegalStateException("User must have a confirmed booking to submit feedback");
        }
        Feedback feedback = new Feedback();
        feedback.setUser(user);
        feedback.setFeedCat(feedCat);
        feedback.setFeedComment(feedComment);

        return feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    @Override
    public List<Feedback> getFeedbacksByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        return feedbackRepository.findByUser(user);
    }

    @Override
    @Transactional
    public Feedback updateFeedback(Long feedbackId, String feedCat, String feedComment) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with id " + feedbackId));

        feedback.setFeedCat(feedCat);
        feedback.setFeedComment(feedComment);

        return feedbackRepository.save(feedback);
    }

    @Override
    @Transactional
    public void deleteFeedback(Long feedbackId) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with id " + feedbackId));
        feedbackRepository.delete(feedback);
    }

    @Override
    public boolean isFeedbackOwner(Long feedbackId, Long userId) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with id " + feedbackId));
        return feedback.getUser().getId().equals(userId);
    }
}