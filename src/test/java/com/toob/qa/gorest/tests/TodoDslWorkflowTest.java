package com.toob.qa.gorest.tests;


import com.toob.qa.gorest.factory.TestDataFactory;
import com.toob.qa.gorest.model.Todo;
import com.toob.qa.gorest.model.User;
import com.toob.qabase.QaBaseTest;
import com.toob.qabase.rest.RestModuleConstants;
import com.toob.qabase.rest.client.RestClient;
import com.toob.qabase.rest.support.HttpSupport;
import io.qameta.allure.*;
import io.restassured.common.mapper.TypeRef;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@QaBaseTest
@Epic("Task Management")
@Feature("User Task Management")
@Story("As a user, I want to manage Tasks or Todos")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TodoDslWorkflowTest extends AbstractGoRestTest {

    public static final TypeRef<List<Todo>> TASK_LIST_TYPE_REF = new TypeRef<>() {};
    private static User user;
    private static Todo todo;

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
    @DisplayName("2️⃣ Assign a todo (POST /todos)")
    void assignTodo() {
        todo = HttpSupport.expect(RestClient.post("/todos", TestDataFactory.randomTodo(user.getId())))
                .created()
                .contentType(RestModuleConstants.DEFAULT_CONTENT_TYPE)
                .fieldEq("user_id", Math.toIntExact(user.getId()))
                .fieldEq("status", "pending")
                .attach()
                .as(Todo.class);
    }

    @Test
    @Order(3)
    @DisplayName("3️⃣ Verify user's todos (GET /todos?user_id=)")
    void verifyUserTodos() {
        List<Todo> todos = HttpSupport.expect(RestClient.get("/todos", Map.of("user_id", user.getId())))
                .ok()
                .contentType(RestModuleConstants.DEFAULT_CONTENT_TYPE)
                .attach()
                .as(TASK_LIST_TYPE_REF);

        assertTrue(todos.stream().anyMatch(t -> t.getId().equals(todo.getId())));
    }

    @Test
    @Order(4)
    @DisplayName("4️⃣ Complete todo (PUT /todos/{id})")
    void completeTodo() {
        todo.setStatus("completed");
        todo = HttpSupport.expect(RestClient.put("/todos/" + todo.getId(), todo))
                .ok()
                .contentType(RestModuleConstants.DEFAULT_CONTENT_TYPE)
                .fieldEq("status", "completed")
                .attach()
                .as(Todo.class);
    }

    @Test
    @Order(5)
    @DisplayName("5️⃣ Cleanup")
    void cleanup() {
        HttpSupport.expect(RestClient.delete("/todos/" + todo.getId())).noContent();
        HttpSupport.expect(RestClient.delete("/users/" + user.getId())).noContent();
    }

}
