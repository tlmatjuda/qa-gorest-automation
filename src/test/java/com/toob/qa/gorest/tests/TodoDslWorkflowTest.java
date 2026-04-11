package com.toob.qa.gorest.tests;


import com.toob.qa.gorest.factory.TestDataFactory;
import com.toob.qa.gorest.model.Todo;
import com.toob.qa.gorest.model.User;
import com.toob.qabase.rest.client.RestClient;
import io.qameta.allure.*;
import io.restassured.common.mapper.TypeRef;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Epic("Task Management")
@Feature("User Task Management")
@Story("As a user, I want to manage Tasks or Todos")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
/**
 * End-to-end workflow test for user → Task lifecycle,
 * showcasing the QABase 2.2.0 unified RestClient chaining style.
 */
class TodoDslWorkflowTest extends AbstractGoRestTest {

    public static final TypeRef<List<Todo>> TASK_LIST_TYPE_REF = new TypeRef<>() {};
    private static User user;
    private static Todo todo;

    // Uses direct RestClient chaining for fluent REST assertions.
    @Test
    @Order(1)
    @DisplayName("1️⃣ Create user")
    void createUser() {
        user = RestClient.post("/users", TestDataFactory.randomUser())
                .created()
                .contentType()
                .attach()
                .as(User.class);
    }

    // Validates assigning a new Task with field checks using the same DSL
    @Test
    @Order(2)
    @DisplayName("2️⃣ Assign a todo (POST /todos)")
    void assignTodo() {
        todo = RestClient.post("/todos", TestDataFactory.randomTodo(user.getId()))
                .created()
                .contentType()
                .fieldEq("user_id", Math.toIntExact(user.getId()))
                .fieldEq("status", "pending")
                .attach()
                .as(Todo.class);
    }

    // Verifies retrieval of todos for the user using the DSL
    @Test
    @Order(3)
    @DisplayName("3️⃣ Verify user's todos (GET /todos?user_id=)")
    void verifyUserTodos() {
        List<Todo> todos = RestClient.get("/todos", Map.of("user_id", user.getId()))
                .ok()
                .contentType()
                .attach()
                .as(TASK_LIST_TYPE_REF);

        assertTrue(todos.stream().anyMatch(t -> t.getId().equals(todo.getId())));
    }

    // Demonstrates update operations with field equality validation via the DSL
    @Test
    @Order(4)
    @DisplayName("4️⃣ Complete todo (PUT /todos/{id})")
    void completeTodo() {
        todo.setStatus("completed");
        todo = RestClient.put("/todos/" + todo.getId(), todo)
                .ok()
                .contentType()
                .fieldEq("status", "completed")
                .attach()
                .as(Todo.class);
    }

    // Cleanup operations validated with the DSL
    @Test
    @Order(5)
    @DisplayName("5️⃣ Cleanup")
    void cleanup() {
        RestClient.delete("/todos/" + todo.getId()).noContent();
        RestClient.delete("/users/" + user.getId()).noContent();
    }

}
