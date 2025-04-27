package com.toob.qa.gorest.tests;


import com.toob.qa.gorest.factory.TestDataFactory;
import com.toob.qa.gorest.model.Comment;
import com.toob.qa.gorest.model.Post;
import com.toob.qa.gorest.model.User;
import io.qameta.allure.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Epic("User Interactions on Blog Posts")
@Feature("User Feedback via Comments")
@Story("As a user, I want to comment on a blog post to engage with its content and share my thoughts")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommentingOnAPostWorkflowTest extends AbstractGoRestTest {

    private static User testUser;
    private static Post testPost;
    private static Comment testComment;

    @Test
    @Order(1)
    @DisplayName("1️⃣ Create a New User")
    @Severity(SeverityLevel.CRITICAL)
    void createUser() {
        testUser = postUser();
        assertNotNull(testUser);
    }

    @Test
    @Order(2)
    @DisplayName("2️⃣ User creates a Post")
    @Severity(SeverityLevel.CRITICAL)
    void createPost() {
        testPost = createUserBlogPost( testUser.getId());
        assertNotNull(testPost);
    }

    @Test
    @Order(3)
    @DisplayName("3️⃣ Comment on a User Post")
    @Severity(SeverityLevel.CRITICAL)
    void createComment() {
        testComment = commentManager.save(TestDataFactory.randomComment( testPost.getId()));
        assertNotNull( testComment.getId(), "Comment Id should not be null" );
        assertEquals( testPost.getId(), testComment.getPostId(), "Blog Post Id should match User Id");
    }

    @Test
    @Order(4)
    @DisplayName("4️⃣ Fetch Comment By Id")
    @Severity(SeverityLevel.NORMAL)
    void updatePost() {
        Comment comment = commentManager.fetchById(testComment.getId());
        assertNotNull(comment);
    }

    @Test
    @Order(5)
    @DisplayName("5️⃣ Clean up Comment, Post and User")
    @Severity(SeverityLevel.NORMAL)
    void deletePost() {
        commentManager.delete(testComment.getId());
        postsManager.delete(testPost.getId());
        userManager.delete(testUser.getId());
    }

}
