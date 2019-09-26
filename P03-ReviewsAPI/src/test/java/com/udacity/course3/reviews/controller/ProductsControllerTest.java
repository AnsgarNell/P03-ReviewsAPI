package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.repository.ProductRepository;
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

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ProductsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<Product> json;

    @MockBean
    ProductRepository productRepository;

    /**
     * Creates pre-requisites for testing, such as an example product or products.
     */
    @Before
    public void setup() {
        Product product = getProduct(1);
        productRepository.save(product);
    }

    /**
     * Tests for successful creation of new product in the system
     * @throws Exception when car creation fails in the system
     */
    @Test
    public void createProduct() throws Exception {
        Product product = getProduct(1);
        mvc.perform(
                post(new URI("/products"))
                        .content(json.write(product).getJson())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());
    }

    private List<Product> getProductList(int count) {
        List<Product> productList = new ArrayList<Product>();
        for (int i = 0; i < count; i++) {
            productList.add(getProduct(i));
        }
        return productList;
    }

    private Product getProduct(int productNumber) {
        Product product = new Product();
        product.setName("Product " + productNumber);
        product.setDescription("This is the description of the product " + productNumber);
        return product;
    }
}
