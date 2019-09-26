package com.udacity.course3.reviews.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;
    private String commentText;
    private Timestamp createdTs;
    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    public Comment() {
    }

    public Comment(Review review) {
        this.review = review;
    }

    public Comment(String title, String commentText, Timestamp createdTs, Review reviewId) {
        this.title = title;
        this.commentText = commentText;
        this.createdTs = createdTs;
        this.review = reviewId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
