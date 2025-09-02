package com.toob.qa.gorest.tests;

import com.toob.qa.gorest.factory.TestDataFactory;
import com.toob.qa.gorest.model.User;
import com.toob.qabase.rest.RestModuleConstants;
import com.toob.qabase.rest.client.RestClient;
import com.toob.qabase.rest.support.HttpSupport;
import io.qameta.allure.*;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@Epic("User Management")
@Feature("User Registration and Profile Update")
@Story("As a user, I want to register and update my profile successfully")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
/**
 * End-to-end workflow test for user registration, profile fetch/update, and deletion.
 * This test leverages the QABase REST DSL (HttpSupport.expect()) which provides fluent and expressive REST assertions
 * including status codes, content type, field equality, and SLA checks.
 * The DSL also integrates with Allure for enhanced reporting and traceability of REST interactions.
 */
class UserManagementWorkflowTest extends AbstractGoRestTest {

    private static User user;

    // Demonstrates QABase DSL chain for POST requests and response assertions
    @Test
    @Order(1)
    @DisplayName("1️⃣ Create user (POST /users)")
    void createUser() {
        // QA Base REST Expect DSL: readable Given/When/Then chain
        Response resp = RestClient.post("/users", TestDataFactory.randomUser());
        user = HttpSupport.expect(resp)
                .created()  // ✅ HTTP 201
                .contentType(RestModuleConstants.DEFAULT_CONTENT_TYPE)
                .attach()   // 📎 Add response body to Allure
                .as(User.class);

        assertNotNull(user.getId(), "New user must have an id");
    }

    // Validates retrieval using DSL assertions including JSON path equality
    @Test
    @Order(2)
    @DisplayName("2️⃣ Fetch user (GET /users/{id})")
    void fetchUser() {
        Response resp = RestClient.get("/users/" + user.getId());
        User found = HttpSupport.expect(resp)
                .ok()   // ✅ HTTP 200
                .contentType(RestModuleConstants.DEFAULT_CONTENT_TYPE)
                .fieldEq("id", Math.toIntExact(user.getId()))    // 🔎 JSON path assertion via DSL
                .attach()
                .as(User.class);

        assertEquals(user.getEmail(), found.getEmail());
    }

    // Shows update and field equality validation with the DSL
    @Test
    @Order(3)
    @DisplayName("3️⃣ Update user (PUT /users/{id})")
    void updateUser() {
        user.setStatus("inactive");

        Response resp = RestClient.put("/users/" + user.getId(), user);
        user = HttpSupport.expect(resp)
                .ok()
                .contentType(RestModuleConstants.DEFAULT_CONTENT_TYPE)
                .fieldEq("status", "inactive")
                .attach()
                .as(User.class);
    }

    // Highlights DSL checks for HTTP 204 (No Content) and SLA timing
    @Test
    @Order(4)
    @DisplayName("4️⃣ Delete user (DELETE /users/{id})")
    void deleteUser() {
        Response resp = RestClient.delete("/users/" + user.getId());
        HttpSupport.expect(resp)
                .noContent()    // ✅ HTTP 204
                .timeUnder(2_000L); // ⏱ SLA check example
    }
}
