package com.toob.qa.gorest.tests;

import com.toob.qabase.rest.AbstractRestTest;
import io.restassured.RestAssured;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;

import static com.toob.qabase.rest.RestModuleConstants.AUTHORIZATION;
import static com.toob.qabase.rest.RestModuleConstants.BEARER;

/**
 * This class extends AbstractRestTest from QABase, which sets up RestAssured
 * (base URI, JSON config, Jackson mapper, etc.) for integration tests.
 *
 * It adds project-specific configuration for the GoRest API, including reading
 * the GOREST_TOKEN environment variable and applying it as a Bearer token header.
 */
abstract class AbstractGoRestTest extends AbstractRestTest {

    // Environment variable for GoRest token
    public static final String ENV_GOREST_TOKEN = "GOREST_TOKEN";

    // Conditionally adds the Authorization header if the token is available
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
