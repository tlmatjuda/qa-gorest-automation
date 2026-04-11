package com.toob.qa.gorest.tests;

import com.toob.qa.gorest.factory.TestDataFactory;
import com.toob.qa.gorest.model.User;
import com.toob.qabase.rest.client.RestClient;
import io.qameta.allure.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@Epic("User Management")
@Feature("User Registration and Profile Update")
@Story("As a user, I want to register and update my profile successfully")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
/**
 * End-to-end workflow test for user registration, profile fetch/update, and deletion.
 * This test leverages QABase 2.2.0's unified REST flow where RestClient calls
 * return a chainable response object for assertions and extraction.
 */
class UserManagementWorkflowTest extends AbstractGoRestTest {

    private static User user;

    // Demonstrates the unified RestClient response/assertion chain for POST requests.
    @Test
    @Order(1)
    @DisplayName("1️⃣ Create user (POST /users)")
    void createUser() {
        user = RestClient.post("/users", TestDataFactory.randomUser())
                .created()  // ✅ HTTP 201
                .contentType()
                .attach()   // 📎 Add response body to Allure
                .as(User.class);

        assertNotNull(user.getId(), "New user must have an id");
    }

    // Validates retrieval using DSL assertions including JSON path equality
    @Test
    @Order(2)
    @DisplayName("2️⃣ Fetch user (GET /users/{id})")
    void fetchUser() {
        User found = RestClient.get("/users/" + user.getId())
                .ok()   // ✅ HTTP 200
                .contentType()
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

        user = RestClient.put("/users/" + user.getId(), user)
                .ok()
                .contentType()
                .fieldEq("status", "inactive")
                .attach()
                .as(User.class);
    }

    // Highlights DSL checks for HTTP 204 (No Content) and SLA timing
    @Test
    @Order(4)
    @DisplayName("4️⃣ Delete user (DELETE /users/{id})")
    void deleteUser() {
        RestClient.delete("/users/" + user.getId())
                .noContent()    // ✅ HTTP 204
                .timeUnder(2_000L); // ⏱ SLA check example
    }
}
