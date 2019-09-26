package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class CommentsControllerTest {

    @Autowired
    private MockMvc mvc;

    private Comment getComment() {
        Comment comment = new Comment();
        comment.setTitle("Comment Title");
        comment.setCommentText("Comment Text");
        Product product = new Product("Product name", "Product description");
        Review review = new Review("Review title", "Review text", null, true, product);
        comment.setReview(review);
        return comment;
    }
}
