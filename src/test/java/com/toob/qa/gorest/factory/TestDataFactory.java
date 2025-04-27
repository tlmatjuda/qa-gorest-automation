package com.toob.qa.gorest.factory;

import com.toob.qa.gorest.model.Comment;
import com.toob.qa.gorest.model.Post;
import com.toob.qa.gorest.model.Todo;
import com.toob.qa.gorest.model.User;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class TestDataFactory {

    public static User randomUser() {
        return User.builder()
                .name("QA BAse " + UUID.randomUUID())
                .email("qabase" + UUID.randomUUID() + "@toobprojects.com")
                .gender("male")
                .status("active")
                .build();
    }

    public static Post randomPost(long userId) {
        return Post.builder()
                .userId( userId) // Random user ID between 0–9999
                .title("QA Base Post " + UUID.randomUUID())
                .body("This is an auto-generated blog post body. ID: " + UUID.randomUUID())
                .build();
    }

    public static Comment randomComment(long postId) {
        return Comment.builder()
                .postId(postId)
                .name("QA Base User " + UUID.randomUUID())
                .email("user_" + UUID.randomUUID() + "@toobprojects.com")
                .body("This is an auto-generated comment body. ID: " + UUID.randomUUID())
                .build();
    }

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
