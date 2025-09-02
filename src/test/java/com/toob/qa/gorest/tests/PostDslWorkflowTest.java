package com.toob.qa.gorest.tests;


import com.toob.qa.gorest.factory.TestDataFactory;
import com.toob.qa.gorest.model.Post;
import com.toob.qa.gorest.model.User;
import com.toob.qabase.QaBaseTest;
import com.toob.qabase.rest.RestModuleConstants;
import com.toob.qabase.rest.client.RestClient;
import com.toob.qabase.rest.support.HttpSupport;
import io.qameta.allure.*;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@Epic("User Blog Post Workflow")
@Feature("Blog Post Management")
@Story("As a user, I want to create and publish a new blog post successfully")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
/**
 * End-to-end workflow test for user → post lifecycle,
 * showcasing the QABase REST DSL (`HttpSupport.expect()`).
 *
 * The DSL makes REST assertions (status codes, content type, field equality, etc.)
 * more fluent and less boilerplate.
 */
class PostDslWorkflowTest extends AbstractGoRestTest {

    private static User user;
    private static Post post;

    // Uses QABase’s HttpSupport.expect() DSL for fluent REST assertions
    @Test
    @Order(1)
    @DisplayName("1️⃣ Create user")
    void createUser() {
        user = HttpSupport.expect(RestClient.post("/users", TestDataFactory.randomUser()))
                .created()
                .contentType(RestModuleConstants.DEFAULT_CONTENT_TYPE)
                .attach()
                .as(User.class);
    }

    // Validates blog post creation using the same fluent DSL
    @Test
    @Order(2)
    @DisplayName("2️⃣ Create post for user")
    void createPost() {
        Response resp = RestClient.post("/posts", TestDataFactory.randomPost(user.getId()));
        post = HttpSupport.expect(resp)
                .created()
                .contentType(RestModuleConstants.DEFAULT_CONTENT_TYPE)
                .fieldEq("user_id", Math.toIntExact(user.getId()))
                .attach()
                .as(Post.class);

        assertNotNull(post.getId());
    }

    // Demonstrates update operations with field equality validation using the DSL
    @Test
    @Order(3)
    @DisplayName("3️⃣ Update post title")
    void updatePost() {
        post.setTitle(post.getTitle() + " (edited)");

        post = HttpSupport.expect(RestClient.put("/posts/" + post.getId(), post))
                .ok()
                .contentType(RestModuleConstants.DEFAULT_CONTENT_TYPE)
                .fieldEq("id", Math.toIntExact(post.getId()))
                .attach()
                .as(Post.class);
    }

    // Uses the DSL to validate deletion (no content and time under threshold)
    @Test
    @Order(4)
    @DisplayName("4️⃣ Delete post")
    void deletePost() {
        HttpSupport.expect(RestClient.delete("/posts/" + post.getId()))
                .noContent().timeUnder(2_000L);
    }

    // Highlights cleanup via the same DSL
    @Test
    @Order(5)
    @DisplayName("5️⃣ Cleanup user")
    void deleteUser() {
        HttpSupport.expect(RestClient.delete("/users/" + user.getId()))
                .noContent();
    }

}
