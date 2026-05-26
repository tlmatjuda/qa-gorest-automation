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

    // Sets one normalized Authorization header so requests never send both a placeholder
    // value from config and a runtime token.
    @BeforeAll
    static void configureAuthorizationIfPresent() {
        if (StringUtils.isNotBlank(System.getProperty(PROP_AUTHORIZATION))) {
            return;
        }

        String authorization = normalizeAuthorization(System.getenv(ENV_GOREST_AUTHORIZATION));
        if (authorization != null) {
            System.setProperty(PROP_AUTHORIZATION, authorization);
            return;
        }

        String token = StringUtils.trimToNull(System.getenv(ENV_GOREST_TOKEN));
        if (token != null) {
            System.setProperty(PROP_AUTHORIZATION, BEARER + " " + token);
        }
    }

    private static String normalizeAuthorization(String authorization) {
        String value = StringUtils.trimToNull(authorization);
        if (value == null) {
            return null;
        }

        return StringUtils.startsWithIgnoreCase(value, BEARER + " ")
                ? value
                : BEARER + " " + value;
    }

}
