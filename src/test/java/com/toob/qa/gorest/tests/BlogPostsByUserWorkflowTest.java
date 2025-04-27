package com.toob.qa.gorest.tests;


import com.toob.qa.gorest.factory.TestDataFactory;
import com.toob.qa.gorest.manager.PostsManager;
import com.toob.qa.gorest.manager.UserManager;
import com.toob.qa.gorest.model.Post;
import com.toob.qa.gorest.model.User;
import com.toob.qabase.http.AbstractHttpTest;
import io.qameta.allure.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
@Epic("User Blog Post Workflow")
@Feature("Blog Post Management")
@Story("As a user, I want to create and publish a new blog post successfully")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BlogPostsByUserWorkflowTest extends AbstractGoRestTest {

    @Autowired
    private UserManager userManager;

    @Autowired
    private PostsManager postsManager;

    private static User testUser;
    private static Post testPost;

    @Test
    @Order(1)
    @DisplayName("1️⃣ Create a New User")
    @Severity(SeverityLevel.CRITICAL)
    void createUser() {
        testUser = userManager.save(TestDataFactory.randomUser());
        assertNotNull( testUser.getId(), "User ID should not be null after creation.");
    }

    @Test
    @Order(2)
    @DisplayName("2️⃣ User creates a Post")
    @Severity(SeverityLevel.CRITICAL)
    void createPost() {
        testPost = createUserBlogPost( testUser.getId());
        assertNotNull( testPost.getTitle(), "Post (  Title ) should not be null after creation.");
        assertNotNull( testPost.getBody(), "Post ( Body ) should not be null after creation.");
    }

    @Test
    @Order(3)
    @DisplayName("3️⃣ Fetch Post & Validate Creation")
    @Severity(SeverityLevel.CRITICAL)
    void fetchPost() {
        Post post = postsManager.fetchById(testPost.getId());
        assertEquals( post.getId(), testPost.getId(), "Blog post IDs should match.");
        assertEquals( testPost.getUserId(), testUser.getId(), "Blog Post Id should match User Id");
    }

    @Test
    @Order(4)
    @DisplayName("4️⃣ Update User Blog Post")
    @Severity(SeverityLevel.NORMAL)
    void updatePost() {
        final String bodyUpdatesMade = "Body Updates Made";
        testPost.setTitle(bodyUpdatesMade);

        final String body = "I have made some updates to see if this will work";
        testPost.setBody(body);
        Post updated = postsManager.udpate(testPost);

        assertEquals(bodyUpdatesMade, updated.getTitle(), "Post Title should match.");
        assertEquals(body, updated.getBody(), "Post body should match.");
    }

    @Test
    @Order(5)
    @DisplayName("5️⃣ Delete Blog Post")
    @Severity(SeverityLevel.BLOCKER)
    void deletePost() {
        postsManager.delete(testPost.getId());
    }

    @Test
    @Order(6)
    @DisplayName("6️⃣ Remove User")
    @Severity(SeverityLevel.BLOCKER)
    void removeUser() {
        userManager.delete(testUser.getId());
    }

}
