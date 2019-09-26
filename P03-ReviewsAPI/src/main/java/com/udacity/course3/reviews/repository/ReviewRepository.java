package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    /**
     * Finds all {@link Review} for a product.
     *
     * @param product The {@link Product} object.
     * @return The list of reviews for the product.
     */
    List<Review> findAllByProduct(Product product);
}
