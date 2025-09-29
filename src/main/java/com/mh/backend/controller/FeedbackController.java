package com.mh.backend.controller;

import com.mh.backend.dto.FeedbackDTO;
import com.mh.backend.dto.FeedbackResponseDTO;
import com.mh.backend.entity.Feedback;
import com.mh.backend.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

//import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/feedbacks")
@Validated
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    // Create feedback
    @PostMapping("/user/{userId}")
    @PreAuthorize("hasRole('USER') and #userId == authentication.principal.id")
    public ResponseEntity<FeedbackResponseDTO> createFeedback(
            @PathVariable Long userId,
            @RequestBody FeedbackDTO feedbackDTO) {
        Feedback feedback = feedbackService.createFeedback(
                userId,
                feedbackDTO.getFeedCat(),
                feedbackDTO.getFeedComment()
        );
        FeedbackResponseDTO response = new FeedbackResponseDTO(
                feedback.getId(),
                feedback.getFeedCat(),
                feedback.getFeedComment(),
                feedback.getUser().getFirstName(),
                feedback.getUser().getLastName()
        );
        return ResponseEntity.ok(response);
    }

    // Get all feedbacks
    @GetMapping
    public ResponseEntity<List<FeedbackResponseDTO>> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        List<FeedbackResponseDTO> response = feedbacks.stream().map(f -> new FeedbackResponseDTO(
                f.getId(),
                f.getFeedCat(),
                f.getFeedComment(),
                f.getUser().getFirstName(),
                f.getUser().getLastName()
        )).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // Get feedbacks by user
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('USER') and #userId == authentication.principal.id")
    public ResponseEntity<List<FeedbackResponseDTO>> getFeedbacksByUser(@PathVariable Long userId) {
        List<Feedback> feedbacks = feedbackService.getFeedbacksByUser(userId);
        List<FeedbackResponseDTO> response = feedbacks.stream().map(f -> new FeedbackResponseDTO(
                f.getId(),
                f.getFeedCat(),
                f.getFeedComment(),
                f.getUser().getFirstName(),
                f.getUser().getLastName()
        )).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // Update feedback
    @PutMapping("/{feedbackId}")
    @PreAuthorize("hasRole('USER') and @feedbackService.isFeedbackOwner(#feedbackId, authentication.principal.id)")
    public ResponseEntity<FeedbackResponseDTO> updateFeedback(
            @PathVariable Long feedbackId,
            @RequestBody FeedbackDTO feedbackDTO) {
        Feedback feedback = feedbackService.updateFeedback(
                feedbackId,
                feedbackDTO.getFeedCat(),
                feedbackDTO.getFeedComment()
        );
        FeedbackResponseDTO response = new FeedbackResponseDTO(
                feedback.getId(),
                feedback.getFeedCat(),
                feedback.getFeedComment(),
                feedback.getUser().getFirstName(),
                feedback.getUser().getLastName()
        );
        return ResponseEntity.ok(response);
    }

    // Delete feedback
    @DeleteMapping("/{feedbackId}")
    @PreAuthorize("hasRole('USER') and @feedbackService.isFeedbackOwner(#feedbackId, authentication.principal.id)")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long feedbackId) {
        feedbackService.deleteFeedback(feedbackId);
        return ResponseEntity.noContent().build();
    }
}