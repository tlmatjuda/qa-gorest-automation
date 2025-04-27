package com.toob.qa.gorest.tests;


import com.toob.qa.gorest.factory.TestDataFactory;
import com.toob.qa.gorest.manager.TodoManager;
import com.toob.qa.gorest.model.Post;
import com.toob.qa.gorest.model.Todo;
import com.toob.qa.gorest.model.User;
import io.qameta.allure.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.toob.qa.gorest.manager.TodoManager.STATUS_PENDING;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Epic("Task Management")
@Feature("User Task Management")
@Story("As a user, I want to manage Tasks or Todos")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TaskAssignmentWorkflowTest extends AbstractGoRestTest {

    private static User testUser;
    private static Todo testTodo;

    @Test
    @Order(1)
    @DisplayName("1️⃣ Create a New User")
    @Severity(SeverityLevel.CRITICAL)
    void createUser() {
        testUser = postUser();
    }

    @Test
    @Order(2)
    @DisplayName("2️⃣ Assign A Task To User")
    @Severity(SeverityLevel.CRITICAL)
    void createPost() {
        testTodo = todoManager.save(TestDataFactory.randomTodo(testUser.getId()));
        assertTrue( testTodo.getStatus().equalsIgnoreCase(TodoManager.STATUS_PENDING));
        assertEquals( testTodo.getUserId(), testUser.getId(), "Task assignment failed");
    }

    @Test
    @Order(3)
    @DisplayName("3️⃣ Fetch User Task List")
    @Severity(SeverityLevel.CRITICAL)
    void fetchPost() {
        List<Todo> todos = todoManager.fetchUserTasks(testUser.getId());
        assertTrue(!todos.isEmpty(), "User does not have Task List");
    }

    @Test
    @Order(4)
    @DisplayName("4️⃣ Complete User Task")
    @Severity(SeverityLevel.CRITICAL)
    void markTaskAsCompleted() {
        assertEquals(testTodo.getStatus(), TodoManager.STATUS_PENDING, "Task should still be PENDING");
        testTodo.setStatus(TodoManager.STATUS_COMPLETE);
        Todo updatedTask = todoManager.udpate(testTodo);
        assertTrue(updatedTask.getStatus().equalsIgnoreCase(TodoManager.STATUS_COMPLETE));
    }

    @Test
    @Order(5)
    @DisplayName("5️⃣ Cleanup Test Data")
    @Severity(SeverityLevel.CRITICAL)
    void remoteTask() {
        todoManager.delete(testTodo.getId());
        userManager.delete(testUser.getId());
    }

}
