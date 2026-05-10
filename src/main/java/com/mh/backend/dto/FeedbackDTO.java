package com.mh.backend.dto;

//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.Size;

public class FeedbackDTO {
//    @NotBlank(message = "Feedback category is required")
//    @Size(max = 100, message = "Feedback category must not exceed 100 characters")
    private String feedCat;

//    @NotBlank(message = "Feedback comment is required")
//    @Size(max = 1000, message = "Feedback comment must not exceed 1000 characters")
    private String feedComment;

    // Constructors
    public FeedbackDTO() {}

    public FeedbackDTO(String feedCat, String feedComment) {
        this.feedCat = feedCat;
        this.feedComment = feedComment;
    }

    // Getters and Setters
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
}