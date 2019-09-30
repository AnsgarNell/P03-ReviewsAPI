package com.udacity.course3.reviews.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

public class MongoReview {
    @Id
    private String id;
    @NotNull
    @NotEmpty
    private String title;
    private String review_text;
    private Timestamp createdTs;
    private boolean recommended;
    @NotNull
    private Product product;

    public MongoReview() {
    }

    public MongoReview(Product product) {
        this.product = product;
    }

    public MongoReview(String title, String review_text, Timestamp createdTs, boolean recommended, Product product) {
        this.title = title;
        this.review_text = review_text;
        this.createdTs = createdTs;
        this.recommended = recommended;
        this.product = product;
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

    public String getReview_text() {
        return review_text;
    }

    public void setReview_text(String review_text) {
        this.review_text = review_text;
    }

    public Timestamp getCreatedTs() {
        return createdTs;
    }

    public void setCreatedTs(Timestamp createdTs) {
        this.createdTs = createdTs;
    }

    public boolean isRecommended() {
        return recommended;
    }

    public void setRecommended(boolean recommended) {
        this.recommended = recommended;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
