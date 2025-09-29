package com.mh.backend.service.impl;

import com.mh.backend.entity.Feedback;
import com.mh.backend.entity.User;
import com.mh.backend.exception.ResourceNotFoundException;
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

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, UserRepository userRepository) {
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Feedback createFeedback(Long userId, String feedCat, String feedComment) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

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