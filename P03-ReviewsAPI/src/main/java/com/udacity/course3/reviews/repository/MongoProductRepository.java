package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.MongoProduct;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoProductRepository extends MongoRepository<MongoProduct, String> {
}
