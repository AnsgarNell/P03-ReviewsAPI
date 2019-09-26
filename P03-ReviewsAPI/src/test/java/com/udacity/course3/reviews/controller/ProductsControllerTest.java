package com.udacity.course3.reviews.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.net.URI;
import java.util.ArrayList;
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
public class ProductsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<Product> json;

    @MockBean
    ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Creates pre-requisites for testing, such as an example product or products.
     */
    @Before
    public void setup() {
        Product product = getProduct();
        given(productRepository.save(any())).willReturn(product);
        given(productRepository.findById(any())).willReturn(java.util.Optional.of(product));
        given(productRepository.findAll()).willReturn(Collections.singletonList(product));
    }

    /**
     * Tests for successful creation of new product in the system
     * @throws Exception when product creation fails in the system
     */
    @Test
    public void createProduct() throws Exception {
        Product product = getProduct();
        mvc.perform(
                post(new URI("/products/"))
                        .content(json.write(product).getJson())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());
    }

    /**
     * Tests for getting product from the system
     * @throws Exception when car creation fails in the system
     */
    @Test
    public void getProductById() throws Exception {
        ResultActions resultActions = mvc.perform(get("/products/1"))
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Product product = getProduct();
        Product responseProduct = objectMapper.readValue(contentAsString, Product.class);
        String responseProductJson = json.write(responseProduct).getJson();
        String carJson = json.write(product).getJson();
        assert (carJson.equals(responseProductJson));
    }

    /**
     * Tests for getting products list from the system
     * @throws Exception when product retrieve fails in the system
     */
    @Test
    public void listProducts() throws Exception {
        Product product = getProduct();
        ResultActions resultActions = mvc.perform(get("/products/"))
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        List<Product> responseProductList = objectMapper.readValue(contentAsString, new TypeReference<List<Product>>(){});
        String responseProductJson = json.write(responseProductList.get(0)).getJson();
        String productJson = json.write(product).getJson();
        assert (productJson.equals(responseProductJson));
    }

    private Product getProduct() {
        Product product = new Product();
        product.setName("Product");
        product.setDescription("This is the description of the product.");
        return product;
    }
}
