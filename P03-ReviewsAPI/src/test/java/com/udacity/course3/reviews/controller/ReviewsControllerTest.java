package com.udacity.course3.reviews.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    private static Product product;

    /**
     * Creates pre-requisites for testing, such as an example comment.
     */
    @Before
    public void setup() {
        product = ProductsControllerTest.getProduct();
        given(productRepository.findById(any())).willReturn(java.util.Optional.ofNullable(product));

        Review review = getReview();
        given(reviewRepository.findById(any())).willReturn(java.util.Optional.of(review));
        given(reviewRepository.findAllByProduct(any())).willReturn(Collections.singletonList(review));
    }

    /**
     * Tests for successful creation of new review in the system
     * @throws Exception when review creation fails in the system
     */
    @Test
    public void createReview() throws Exception {
        Review review = getReview();
        mvc.perform(
                post(new URI("/reviews/products/1"))
                        .content(json.write(review).getJson())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

    }

    /**
     * Tests for getting reviews list from the system
     * @throws Exception when reviews retrieve fails in the system
     */
    @Test
    public void listComments() throws Exception {
        Review review = getReview();
        ResultActions resultActions = mvc.perform(get("/reviews/products/1"))
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        List<Review> responseReviewList = objectMapper.readValue(contentAsString, new TypeReference<List<Review>>(){});
        String responseReviewJson = json.write(responseReviewList.get(0)).getJson();
        String commentJson = json.write(review).getJson();
        assert (commentJson.equals(responseReviewJson));
    }

    public static Review getReview() {
        return new Review("Review title", "Review text", null, true, product);
    }
}
