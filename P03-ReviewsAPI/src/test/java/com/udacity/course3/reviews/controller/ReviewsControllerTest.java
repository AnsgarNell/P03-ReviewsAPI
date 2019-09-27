package com.udacity.course3.reviews.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ReviewsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<Review> json;

    @MockBean
    private ReviewRepository reviewRepository;

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Creates pre-requisites for testing, such as an example comment.
     */
    @Before
    public void setup() {
        Review review = getReview();
        given(reviewRepository.findById(any())).willReturn(java.util.Optional.of(review));
    }

    private Review getReview() {
        Product product = new Product("Product name", "Product description");
        return new Review("Review title", "Review text", null, true, product);
    }
}
