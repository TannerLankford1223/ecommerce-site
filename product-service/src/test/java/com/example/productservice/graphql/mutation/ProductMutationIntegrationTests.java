package com.example.productservice.graphql.mutation;

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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = { ProductServiceApplication.class })
@ActiveProfiles(profiles = { "test" })
public class ProductMutationIntegrationTests {

    private static final String MUTATION_REQUEST_PATH = "graphql/request/%s.query";

    private static final String MUTATION_RESPONSE_PATH = "graphql/response/%s.json";

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @Test
    @DisplayName("Should add a new product successfully")
    public void addProduct_Success() throws IOException, JSONException {
        String testName = "add_product";
        GraphQLResponse response = graphQLTestTemplate.postForResource(String.format(MUTATION_REQUEST_PATH, testName));

        String expectedResponseBody = readResponseFile(String.format(MUTATION_RESPONSE_PATH, testName));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(expectedResponseBody, response.getRawResponse().getBody(), true);
    }

    @Test
    @DisplayName("Should throw exception if client tries to add a duplicate product")
    public void addProduct_ProductExists_ThrowsException() throws IOException, JSONException {
        String testName = "add_product_product_exists";
        GraphQLResponse response = graphQLTestTemplate.postForResource(String.format(MUTATION_REQUEST_PATH, testName));

        System.out.println(response.getRawResponse().getBody());

        String expectedResponseBody = readResponseFile(String.format(MUTATION_RESPONSE_PATH, testName));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(expectedResponseBody, response.getRawResponse().getBody(), true);
    }

    @Test
    @DisplayName("Should throw exception if client tries to add a product with a non-existent category")
    public void addProduct_CategoryNonExistent_ThrowsException() throws IOException, JSONException {
        String testName = "add_product_non_existent_category";
        GraphQLResponse response = graphQLTestTemplate.postForResource(String.format(MUTATION_REQUEST_PATH, testName));

        String expectedResponseBody = readResponseFile(String.format(MUTATION_RESPONSE_PATH, testName));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(expectedResponseBody, response.getRawResponse().getBody(), true);
    }

    @Test
    @DisplayName("Should update a product successfully")
    public void updateProduct_Success() throws IOException, JSONException {
        String testName = "update_product";
        GraphQLResponse response = graphQLTestTemplate.postForResource(String.format(MUTATION_REQUEST_PATH, testName));

        String expectedResponseBody = readResponseFile(String.format(MUTATION_RESPONSE_PATH, testName));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(expectedResponseBody, response.getRawResponse().getBody(), true);
    }

    @Test
    @DisplayName("Should delete a product successfully")
    public void deleteProduct_Success() throws IOException, JSONException {
        String testName = "delete_product";
        GraphQLResponse response = graphQLTestTemplate.postForResource(String.format(MUTATION_REQUEST_PATH, testName));

        String expectedResponseBody = readResponseFile(String.format(MUTATION_RESPONSE_PATH, testName));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(expectedResponseBody, response.getRawResponse().getBody(), true);
    }

    @Test
    @DisplayName("Should throw exception client tries to delete a non-existent product")
    public void deleteProduct_ProductNonExistent_ThrowsException() throws IOException, JSONException {
        String testName = "delete_product_non_existent_product";
        GraphQLResponse response = graphQLTestTemplate.postForResource(String.format(MUTATION_REQUEST_PATH, testName));

        String expectedResponseBody = readResponseFile(String.format(MUTATION_RESPONSE_PATH, testName));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(expectedResponseBody, response.getRawResponse().getBody(), true);
    }

}
