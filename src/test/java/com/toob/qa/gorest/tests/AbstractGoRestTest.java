package com.toob.qa.gorest.tests;

import com.toob.qabase.rest.QaRestTest;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;

import static com.toob.qabase.rest.RestModuleConstants.BEARER;

/**
 * This class extends AbstractRestTest from QABase, which sets up RestAssured
 * (base URI, JSON config, Jackson mapper, etc.) for integration tests.
 *
 * It also preserves the older GOREST_TOKEN auth workflow by translating it into
 * the new qabase.rest.headers.authorization config property when needed.
 */
@QaRestTest
abstract class AbstractGoRestTest {

    // Environment variable for GoRest token
    public static final String ENV_GOREST_TOKEN = "GOREST_TOKEN";
    public static final String ENV_GOREST_AUTHORIZATION = "GOREST_AUTHORIZATION";
    public static final String PROP_AUTHORIZATION = "qabase.rest.headers.authorization";

    // Keeps legacy token setup working while default headers are sourced from config.
    @BeforeAll
    static void configureAuthorizationIfPresent() {
        if (StringUtils.isNotBlank(System.getProperty(PROP_AUTHORIZATION))
                || StringUtils.isNotBlank(System.getenv(ENV_GOREST_AUTHORIZATION))) {
            return;
        }

        String token = System.getenv(ENV_GOREST_TOKEN);
        if (StringUtils.isNotBlank(token)) {
            System.setProperty(PROP_AUTHORIZATION, BEARER + " " + token);
        }
    }

}
