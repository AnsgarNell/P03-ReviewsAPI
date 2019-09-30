package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.MongoReview;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.MongoReviewRepository;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with review entity.
 */
@RestController
public class ReviewsController {

    // Wire JPA repositories here
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    MongoReviewRepository mongoReviewRepository;
    @Autowired
    ProductRepository productRepository;

    /**
     * Creates a review for a product.
     * <p>
     * 1. Add argument for review entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of product.
     * 3. If product not found, return NOT_FOUND.
     * 4. If found, save review.
     *
     * @param productId The id of the product.
     * @return The created review or 404 if product id is not found.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.POST)
    public ResponseEntity<?> createReviewForProduct(@PathVariable("productId") Integer productId, @Valid @RequestBody Review review) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            review.setProduct(optionalProduct.get());
            reviewRepository.save(review);

            // Add review to MongoDB
            MongoReview mongoReview = new MongoReview();
            mongoReview.setId(review.getId().toString());
            mongoReview.setCreatedTs(review.getCreatedTs());
            mongoReview.setProduct(review.getProduct());
            mongoReview.setRecommended(review.isRecommended());
            mongoReview.setReview_text(review.getReview_text());
            mongoReview.setTitle(review.getTitle());
            mongoReviewRepository.save(mongoReview);

            return ResponseEntity.ok(review);
        }
        else throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
    }

    /**
     * Lists reviews by product.
     *
     * @param productId The id of the product.
     * @return The list of reviews.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.GET)
    public ResponseEntity<List<?>> listReviewsForProduct(@PathVariable("productId") Integer productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            List<Review> reviewList = reviewRepository.findAllByProduct(optionalProduct.get());

            List<MongoReview> mongoReviewList = new ArrayList<>();
            reviewList.forEach(review -> {
                Optional<MongoReview> optionalMongoReview = mongoReviewRepository.findById(review.getId().toString());
                if (optionalMongoReview.isPresent()) mongoReviewList.add(optionalMongoReview.get());
            });

            return ResponseEntity.ok(mongoReviewList);
        }
        else throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
    }
}