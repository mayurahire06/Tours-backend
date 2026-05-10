package com.mh.backend.dto;

public class FeedbackResponseDTO {
    private Long id;
    private String feedCat;
    private String feedComment;
    private String firstName;
    private String lastName;

    public FeedbackResponseDTO(Long id, String feedCat, String feedComment, String firstName, String lastName) {
        this.id = id;
        this.feedCat = feedCat;
        this.feedComment = feedComment;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}