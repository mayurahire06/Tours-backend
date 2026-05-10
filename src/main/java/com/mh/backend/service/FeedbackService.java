package com.mh.backend.service;

import com.mh.backend.entity.Feedback;

import java.util.List;

public interface FeedbackService {
    Feedback createFeedback(Long userId, String feedCat, String feedComment);
    List<Feedback> getAllFeedbacks();
    List<Feedback> getFeedbacksByUser(Long userId);
    Feedback updateFeedback(Long feedbackId, String feedCat, String feedComment);
    void deleteFeedback(Long feedbackId);
    boolean isFeedbackOwner(Long feedbackId, Long userId);
}