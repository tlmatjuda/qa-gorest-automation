package com.toob.qa.gorest.tests;

import com.toob.qabase.rest.AbstractRestTest;
import io.restassured.RestAssured;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;

import static com.toob.qabase.rest.RestModuleConstants.AUTHORIZATION;
import static com.toob.qabase.rest.RestModuleConstants.BEARER;

abstract class AbstractGoRestTest extends AbstractRestTest {

    public static final String ENV_GOREST_TOKEN = "GOREST_TOKEN";

    @BeforeAll
    static void addAuthIfPresent() {
        // Read env var directly (Java 17+ friendly)
        String token = System.getenv(ENV_GOREST_TOKEN);
        if (StringUtils.isNotBlank(token)) {
            RestAssured.requestSpecification
                    .header(AUTHORIZATION, BEARER + " " + token);
        }
    }

}
