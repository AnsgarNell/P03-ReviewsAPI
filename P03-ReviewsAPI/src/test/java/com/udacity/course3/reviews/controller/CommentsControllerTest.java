package com.udacity.course3.reviews.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.CommentRepository;
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
public class CommentsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<Comment> json;

    @MockBean
    private ReviewRepository reviewRepository;

    @MockBean
    private CommentRepository commentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static Product product;
    private static Review review;

    /**
     * Creates pre-requisites for testing, such as an example comment.
     */
    @Before
    public void setup() {
        // Should be product = mock(Product.class); but it only works with real objects
        product = ProductsControllerTest.getProduct();

        // Should be review = mock(Review.class); but it only works with real objects
        review = ReviewsControllerTest.getReview();

        review.setProduct(product);
        given(reviewRepository.findById(any())).willReturn(java.util.Optional.of(review));

        Comment comment = getComment();
        given(commentRepository.save(any())).willReturn(comment);
        given(commentRepository.findById(any())).willReturn(java.util.Optional.of(comment));
        given(commentRepository.findAllByReview(any())).willReturn(Collections.singletonList(comment));
    }

    /**
     * Tests for successful creation of new comment in the system
     * @throws Exception when comment creation fails in the system
     */
    @Test
    public void createComment() throws Exception {
        Comment comment = getComment();
        mvc.perform(
                post(new URI("/comments/reviews/1"))
                        .content(json.write(comment).getJson())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    /**
     * Tests for getting comments list from the system
     * @throws Exception when comments retrieve fails in the system
     */
    @Test
    public void listComments() throws Exception {
        Comment comment = getComment();
        ResultActions resultActions = mvc.perform(get("/comments/reviews/1"))
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        List<Comment> responseCommentList = objectMapper.readValue(contentAsString, new TypeReference<List<Comment>>(){});
        String responseCommentJson = json.write(responseCommentList.get(0)).getJson();
        String commentJson = json.write(comment).getJson();
        assert (commentJson.equals(responseCommentJson));
    }

    public static  Comment getComment() {
        Comment comment = new Comment();
        comment.setTitle("Comment Title");
        comment.setCommentText("Comment Text");
        comment.setReview(review);
        return comment;
    }
}
