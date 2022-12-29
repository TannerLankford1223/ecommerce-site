package com.example.productservice.graphql.query;

import com.example.productservice.ProductServiceApplication;
import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static com.example.productservice.util.JsonParsing.readResponseFile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ProductServiceApplication.class)
@ActiveProfiles(profiles = { "test" })
public class ProductQueryIntegrationTests {
    private static final String QUERY_REQUEST_PATH = "graphql/request/%s.query";

    private static final String QUERY_RESPONSE_PATH = "graphql/response/%s.json";

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @Test
    @DisplayName("Should response to query with a paginated list of all products")
    public void allProducts_ReturnsAllProducts() throws IOException, JSONException {
        String testName = "all_products";
        GraphQLResponse response = graphQLTestTemplate.postForResource(String.format(QUERY_REQUEST_PATH, testName));

        String expectedResponseBody = readResponseFile(String.format(QUERY_RESPONSE_PATH, testName));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(expectedResponseBody, response.getRawResponse().getBody(), true);
    }

    @Test
    @DisplayName("Should respond to query with a paginated list based on page and page size")
    public void allProducts_ReturnsPaginatedProducts() throws IOException, JSONException {
        String testName = "all_products_paginated";
        GraphQLResponse response = graphQLTestTemplate.postForResource(String.format(QUERY_REQUEST_PATH, testName));

        String expectedResponseBody = readResponseFile(String.format(QUERY_RESPONSE_PATH, testName));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(expectedResponseBody, response.getRawResponse().getBody(), true);
    }

    @Test
    @DisplayName("Should response to query with a paginated list of all products with search term")
    public void allProducts_WithSearchTerm_ReturnsAllProductsContainingSearchTerm() throws IOException, JSONException {
        String testName = "all_products_with_search_term";
        GraphQLResponse response = graphQLTestTemplate.postForResource(String.format(QUERY_REQUEST_PATH, testName));

        String expectedresponseBody = readResponseFile(String.format(QUERY_RESPONSE_PATH, testName));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(expectedresponseBody, response.getRawResponse().getBody(), true);
    }

    @Test
    @DisplayName("Should respond to query with a paginated list of all products with category")
    public void allProducts_WithCategory_ReturnsAllProductsWithCategory() throws IOException, JSONException {
        String testName = "all_products_with_category";
        GraphQLResponse response = graphQLTestTemplate.postForResource(String.format(QUERY_REQUEST_PATH, testName));

        String expectedresponseBody = readResponseFile(String.format(QUERY_RESPONSE_PATH, testName));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(expectedresponseBody, response.getRawResponse().getBody(), true);
    }

    @Test
    @DisplayName("Should respond to query with a paginated list of all products with search term and category")
    public void allProducts_WithSearchTermAndCategory_ReturnsAllProductsContainingSearchTermAndCategory()
            throws IOException, JSONException {
        String testName = "all_products_with_search_term_and_category";
        GraphQLResponse response = graphQLTestTemplate.postForResource(String.format(QUERY_REQUEST_PATH, testName));

        String expectedresponseBody = readResponseFile(String.format(QUERY_RESPONSE_PATH, testName));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(expectedresponseBody, response.getRawResponse().getBody(), true);
    }

    @Test
    @DisplayName("Should respond to query with product that contains the provided ID")
    public void productById_ReturnsProduct() throws IOException, JSONException {
        String testName = "product_by_id";
        GraphQLResponse response = graphQLTestTemplate.postForResource(String.format(QUERY_REQUEST_PATH, testName));

        String expectedresponseBody = readResponseFile(String.format(QUERY_RESPONSE_PATH, testName));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(expectedresponseBody, response.getRawResponse().getBody(), true);
    }

    @Test
    @DisplayName("Should respond to query containing an invalid id with an error")
    public void productById_InvalidId_ReturnsError() throws IOException, JSONException {
        String testName = "product_by_id_invalid";

        GraphQLResponse response = graphQLTestTemplate.postForResource(String.format(QUERY_REQUEST_PATH, testName));

        String expectedresponseBody = readResponseFile(String.format(QUERY_RESPONSE_PATH, testName));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(expectedresponseBody, response.getRawResponse().getBody(), true);
    }

}
