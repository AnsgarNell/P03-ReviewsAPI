package com.udacity.course3.reviews.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

public class MongoComment {
    @Id
    private String id;
    @NotNull
    @NotEmpty
    private String title;
    private String commentText;
    private Timestamp createdTs;
    private Review review;

    public MongoComment() {
    }

    public MongoComment(Review review) {
        this.review = review;
    }

    public MongoComment(String title, String commentText, Timestamp createdTs, Review reviewId) {
        this.title = title;
        this.commentText = commentText;
        this.createdTs = createdTs;
        this.review = reviewId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Timestamp getCreatedTs() {
        return createdTs;
    }

    public void setCreatedTs(Timestamp createdTs) {
        this.createdTs = createdTs;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
