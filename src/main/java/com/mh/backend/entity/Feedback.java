package com.mh.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedbacks")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

//    @NotBlank(message = "Feedback category is required")
//    @Size(max = 100, message = "Feedback category must not exceed 100 characters")
    @Column(name = "feed_cat", nullable = false, length = 100)
    private String feedCat;

//    @NotBlank(message = "Feedback comment is required")
//    @Size(max = 1000, message = "Feedback comment must not exceed 1000 characters")
    @Column(name = "feed_comment", nullable = false, columnDefinition = "TEXT")
    private String feedComment;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    // Constructors
    public Feedback() {}

    public Feedback(User user, String feedCat, String feedComment) {
        this.user = user;
        this.feedCat = feedCat;
        this.feedComment = feedComment;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFeedCat() {
        return feedCat;
    }

    public void setFeedCat(String feedCat) {
        this.feedCat = feedCat;
    }

    public String getFeedComment() {
        return feedComment;
    }

    public void setFeedComment(String feedComment) {
        this.feedComment = feedComment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}