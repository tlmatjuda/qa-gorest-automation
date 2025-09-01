package com.toob.qa.gorest.tests;


import com.toob.qa.gorest.factory.TestDataFactory;
import com.toob.qa.gorest.model.Comment;
import com.toob.qa.gorest.model.Post;
import com.toob.qa.gorest.model.User;
import com.toob.qabase.QaBaseTest;
import com.toob.qabase.rest.RestModuleConstants;
import com.toob.qabase.rest.client.RestClient;
import com.toob.qabase.rest.support.HttpSupport;
import io.qameta.allure.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@QaBaseTest
@Epic("User Interactions on Blog Posts")
@Feature("User Feedback via Comments")
@Story("As a user, I want to comment on a blog post to engage with its content and share my thoughts")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommentDslWorkflowTest extends AbstractGoRestTest {

    private static User user;
    private static Post post;
    private static Comment comment;

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

    @Test
    @Order(2)
    @DisplayName("2️⃣ Create post")
    void createPost() {
        post = HttpSupport.expect(RestClient.post("/posts", TestDataFactory.randomPost(user.getId())))
                .created()
                .contentType(RestModuleConstants.DEFAULT_CONTENT_TYPE)
                .fieldEq("user_id", Math.toIntExact(user.getId()))
                .attach()
                .as(Post.class);
    }

    @Test
    @Order(3)
    @DisplayName("3️⃣ Add comment")
    void addComment() {
        comment = HttpSupport.expect(RestClient.post("/comments", TestDataFactory.randomComment(post.getId())))
                .created()
                .contentType(RestModuleConstants.DEFAULT_CONTENT_TYPE)
                .fieldEq("post_id", Math.toIntExact(post.getId()))
                .attach()
                .as(Comment.class);

        assertNotNull(comment.getId());
    }

    @Test
    @Order(4)
    @DisplayName("4️⃣ Fetch comment")
    void fetchComment() {
        Comment found = HttpSupport.expect(RestClient.get("/comments/" + comment.getId()))
                .ok()
                .contentType(RestModuleConstants.DEFAULT_CONTENT_TYPE)
                .fieldEq("id", Math.toIntExact(comment.getId()))
                .attach()
                .as(Comment.class);

        assertEquals(comment.getEmail(), found.getEmail());
    }

    @Test
    @Order(5)
    @DisplayName("5️⃣ Cleanup")
    void cleanup() {
        HttpSupport.expect(RestClient.delete("/comments/" + comment.getId())).noContent();
        HttpSupport.expect(RestClient.delete("/posts/" + post.getId())).noContent();
        HttpSupport.expect(RestClient.delete("/users/" + user.getId())).noContent();
    }

}
