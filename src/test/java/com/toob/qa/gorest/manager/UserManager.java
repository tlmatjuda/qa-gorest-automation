package com.toob.qa.gorest.manager;


import com.toob.qa.gorest.model.User;
import com.toob.qabase.http.RestClient;
import com.toob.qabase.http.support.HttpSupport;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j
@Service
public class UserManager extends AbstractManager {

    public static final String URL_USERS = "/users";


    public User save(User user) {
        assertNull(user.getId(), "[ user.id ] has to be null");
        Response response = RestClient.post(String.format(TEMPLATE_ENTITY_PATH, URL_USERS), user);
        HttpSupport.created( response);
        return response.as(User.class);
    }

    public User fetchById(long id) {
        Response response = RestClient.get(String.format(TEMPLATE_ENTITY_SLASH_ID_PATH, URL_USERS, id));
        HttpSupport.allOkay( response);
        return response.as(User.class);
    }

    public User udpate(User user) {
        assertNotNull(user.getId(), "[ user.id ] is required");
        Response response = RestClient.put(String.format(TEMPLATE_ENTITY_SLASH_ID_PATH, URL_USERS, user.getId()), user);
        HttpSupport.allOkay( response);
        return response.as(User.class);
    }

    public void delete(long id) {
        Response response = RestClient.delete(String.format(TEMPLATE_ENTITY_SLASH_ID_PATH, URL_USERS, id));
        HttpSupport.allOkayWithoutContent( response);
    }


}
