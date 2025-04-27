package com.toob.qa.gorest.tests;

import com.toob.qa.gorest.factory.TestDataFactory;
import com.toob.qa.gorest.manager.UserManager;
import com.toob.qa.gorest.model.User;
import com.toob.qabase.http.AbstractHttpTest;
import io.qameta.allure.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
@Epic("User Management")
@Feature("User Registration and Profile Update")
@Story("As a user, I want to register and update my profile successfully")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class UserManagementWorkflowTest extends AbstractHttpTest {

    @Autowired
    private UserManager userManager;
    private static User testUser;

    @Test
    @Order(1)
    @DisplayName("1️⃣ Create a New User")
    @Severity(SeverityLevel.CRITICAL)
    void createUser() {
        testUser = userManager.save(TestDataFactory.randomUser());
        assertNotNull(testUser.getId(), "User ID should not be null after creation.");
    }

    @Test
    @Order(2)
    @DisplayName("2️⃣ Fetch Created User and Validate")
    @Severity(SeverityLevel.CRITICAL)
    void fetchCreatedUser() {
        User fetched = userManager.fetchById(testUser.getId());
        assertEquals(testUser.getEmail(), fetched.getEmail(), "Fetched user email should match.");
    }

    @Test
    @Order(3)
    @DisplayName("3️⃣ Update User Information")
    @Severity(SeverityLevel.NORMAL)
    void updateUserProfile() {
        testUser.setStatus("inactive");
        User updated = userManager.udpate(testUser);
        assertEquals("inactive", updated.getStatus(), "User status should be updated to inactive.");
    }

    @Test
    @Order(4)
    @DisplayName("4️⃣ Delete User")
    @Severity(SeverityLevel.BLOCKER)
    void deleteUser() {
        userManager.delete(testUser.getId());
    }
}
