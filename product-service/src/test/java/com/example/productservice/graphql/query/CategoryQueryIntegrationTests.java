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
public class CategoryQueryIntegrationTests {

    private static final String QUERY_REQUEST_PATH = "graphql/request/%s.query";

    private static final String QUERY_RESPONSE_PATH = "graphql/response/%s.json";

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @Test
    @DisplayName("Should return a list of all categories")
    public void allCategories_ReturnsListOfAllCategories() throws IOException, JSONException {
        String testName = "all_categories";
        GraphQLResponse response = graphQLTestTemplate.postForResource(String.format(QUERY_REQUEST_PATH, testName));

        String expectedResponseBody = readResponseFile(String.format(QUERY_RESPONSE_PATH, testName));
        System.out.println(response.getRawResponse().getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(expectedResponseBody, response.getRawResponse().getBody(), true);
    }
}
