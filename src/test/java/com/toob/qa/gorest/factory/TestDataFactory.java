
package com.toob.qa.gorest.factory;

import com.toob.qa.gorest.model.Comment;
import com.toob.qa.gorest.model.Post;
import com.toob.qa.gorest.model.Todo;
import com.toob.qa.gorest.model.User;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Factory class for generating random test data objects (User, Post, Comment, Task)
 * for QABase automation tests. Each method provides randomized or auto-generated
 * values for the relevant fields, useful for test scenarios.
 */
public class TestDataFactory {

    /**
     * Generates a random User object with a unique name and email.
     * Gender is set to "male" and status to "active".
     *
     * @return User with randomized name and email
     */
    public static User randomUser() {
        return User.builder()
                .name("QA BAse " + UUID.randomUUID())
                .email("qabase" + UUID.randomUUID() + "@toobprojects.com")
                .gender("male")
                .status("active")
                .build();
    }

    /**
     * Generates a random Post object associated with the provided userId.
     * Title and body are randomized for test uniqueness.
     *
     * @param userId the ID of the user to associate with this Post
     * @return Post with randomized title and body
     */
    public static Post randomPost(long userId) {
        return Post.builder()
                .userId( userId) // Random user ID between 0–9999
                .title("QA Base Post " + UUID.randomUUID())
                .body("This is an auto-generated blog post body. ID: " + UUID.randomUUID())
                .build();
    }

    /**
     * Generates a random Comment object associated with the provided postId.
     * Name, email, and body are randomized for test uniqueness.
     *
     * @param postId the ID of the post to associate with this Comment
     * @return Comment with randomized fields
     */
    public static Comment randomComment(long postId) {
        return Comment.builder()
                .postId(postId)
                .name("QA Base User " + UUID.randomUUID())
                .email("user_" + UUID.randomUUID() + "@toobprojects.com")
                .body("This is an auto-generated comment body. ID: " + UUID.randomUUID())
                .build();
    }

    /**
     * Generates a random Task object associated with the provided userId.
     * Title is randomized, due date is set to 7 days from now, and status is "pending".
     *
     * @param userId the ID of the user to associate with this Task
     * @return Task with randomized title and future due date
     */
    public static Todo randomTodo(long userId) {
        return Todo.builder()
                .userId(userId)
                .title("QA Base Todo " + UUID.randomUUID())
                .dueOn(OffsetDateTime.now().plusDays(7)
                        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)) // Due 7 days from now
                .status("pending")
                .build();
    }

}
