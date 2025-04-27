package com.toob.qa.gorest.tests;

import com.toob.qa.gorest.factory.TestDataFactory;
import com.toob.qa.gorest.manager.PostsManager;
import com.toob.qa.gorest.manager.UserManager;
import com.toob.qa.gorest.model.Post;
import com.toob.qa.gorest.model.User;
import com.toob.qabase.http.AbstractHttpTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
abstract class AbstractGoRestTest extends AbstractHttpTest {

    @Autowired
    private UserManager userManager;

    @Autowired
    private PostsManager postsManager;

    User postUser() {
        User savedUser = userManager.save(TestDataFactory.randomUser());
        assertNotNull(savedUser.getId(), "User ID should not be null after creation.");
        return savedUser;
    }

    Post createUserBlogPost(long userId) {
        Post freshPost = TestDataFactory.randomPost(userId);
        Post savedPost = postsManager.save(freshPost);
        assertNotNull(savedPost.getId(), "Post ID should not be null after creation.");
        return savedPost;
    }

}
