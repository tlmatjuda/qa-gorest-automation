package com.toob.qa.gorest.manager;


import com.toob.qa.gorest.model.Todo;
import com.toob.qabase.http.RestClient;
import com.toob.qabase.http.support.HttpSupport;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j
@Service
public class TodoManager extends AbstractManager {

    public static final String URL_TODOS = "/todos";

    public static final String PARAM_USER_ID = "userId";

    public static final String STATUS_PENDING = "pending";
    public static final String STATUS_COMPLETE= "completed";


    public Todo save(Todo todo) {
        assertNull(todo.getId(), "[ todo.id ] has to be null");
        Response response = RestClient.post(String.format(TEMPLATE_ENTITY_PATH, URL_TODOS), todo);
        HttpSupport.created( response);
        return response.as(Todo.class);
    }

    public List<Todo> fetchUserTasks(long userId) {
        Map<String, Long> queryParams = Map.of(PARAM_USER_ID, userId);
        Response response = RestClient.get(URL_TODOS, queryParams);
        HttpSupport.allOkay( response);
        return response.jsonPath().getList("", Todo.class);
    }

    public Todo udpate(Todo todo) {
        assertNotNull(todo.getId(), "[ todo.id ] is required");
        Response response = RestClient.put(String.format(TEMPLATE_ENTITY_SLASH_ID_PATH, URL_TODOS, todo.getId()), todo);
        HttpSupport.allOkay( response);
        return response.as(Todo.class);
    }

    public void delete(long id) {
        Response response = RestClient.delete(String.format(TEMPLATE_ENTITY_SLASH_ID_PATH, URL_TODOS, id));
        HttpSupport.allOkayWithoutContent( response);
    }


}
