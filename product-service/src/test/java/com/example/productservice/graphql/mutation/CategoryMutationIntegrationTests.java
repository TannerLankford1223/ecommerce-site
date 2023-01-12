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
public class CategoryMutationIntegrationTests {

    private static final String MUTATION_REQUEST_PATH = "graphql/request/%s.query";

    private static final String MUTATION_RESPONSE_PATH = "graphql/response/%s.json";

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @Test
    @DisplayName("Should add a new category successfully")
    public void addCategory_Success() throws IOException, JSONException {
        String testName = "add_category";
        GraphQLResponse response = graphQLTestTemplate.postForResource(String.format(MUTATION_REQUEST_PATH, testName));

        String expectedResponseBody = readResponseFile(String.format(MUTATION_RESPONSE_PATH, testName));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(expectedResponseBody, response.getRawResponse().getBody(), true);
    }

    @Test
    @DisplayName("Should throw exception if client tries to add a duplicate category")
    public void addCategory_CategoryExists_ThrowsException() throws IOException, JSONException {
        String testName = "add_category_category_exists";
        GraphQLResponse response = graphQLTestTemplate.postForResource(String.format(MUTATION_REQUEST_PATH, testName));

        System.out.println(response.getRawResponse().getBody());

        String expectedResponseBody = readResponseFile(String.format(MUTATION_RESPONSE_PATH, testName));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(expectedResponseBody, response.getRawResponse().getBody(), true);
    }

    @Test
    @DisplayName("Should delete a category successfully")
    public void deleteCategory_Success() throws IOException, JSONException {
        String testName = "delete_category";
        GraphQLResponse response = graphQLTestTemplate.postForResource(String.format(MUTATION_REQUEST_PATH, testName));

        String expectedResponseBody = readResponseFile(String.format(MUTATION_RESPONSE_PATH, testName));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(expectedResponseBody, response.getRawResponse().getBody(), true);
    }

    @Test
    @DisplayName("Should throw exception client tries to delete a non-existent category")
    public void deleteCategory_CategoryNonExistent_ThrowsException() throws IOException, JSONException {
        String testName = "delete_category_non_existent_category";
        GraphQLResponse response = graphQLTestTemplate.postForResource(String.format(MUTATION_REQUEST_PATH, testName));

        String expectedResponseBody = readResponseFile(String.format(MUTATION_RESPONSE_PATH, testName));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(expectedResponseBody, response.getRawResponse().getBody(), true);
    }
}
