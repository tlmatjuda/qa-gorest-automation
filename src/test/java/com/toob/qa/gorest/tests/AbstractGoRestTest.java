package com.toob.qa.gorest.tests;

import com.toob.qabase.rest.AbstractRestTest;
import io.restassured.RestAssured;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;

abstract class AbstractGoRestTest extends AbstractRestTest {

    @BeforeAll
    static void addAuthIfPresent() {
        // Read env var directly (Java 17+ friendly)
        String token = System.getenv("GOREST_TOKEN");
        if (StringUtils.isNotBlank(token)) {
            RestAssured.requestSpecification
                    .header("Authorization", "Bearer " + token);
        }
    }

}
