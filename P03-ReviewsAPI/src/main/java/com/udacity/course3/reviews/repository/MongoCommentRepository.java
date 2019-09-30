package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.MongoComment;
import com.udacity.course3.reviews.entity.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoCommentRepository extends MongoRepository<MongoComment, String> {
    /**
     * Finds all {@link MongoComment} for a review.
     *
     * @param review The {@link Review} object.
     * @return The list of comments for the review.
     */
    List<MongoComment> findAllByReview(Review review);
}
