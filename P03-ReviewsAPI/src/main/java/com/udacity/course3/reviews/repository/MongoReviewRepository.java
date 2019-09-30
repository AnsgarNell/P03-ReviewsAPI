package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.MongoReview;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoReviewRepository extends MongoRepository<MongoReview, String> {
    /**
     * Finds all {@link MongoReview} for a product.
     *
     * @param product The {@link Product} object.
     * @return The list of reviews for the product.
     */
    List<MongoReview> findAllByProduct(Product product);
}
