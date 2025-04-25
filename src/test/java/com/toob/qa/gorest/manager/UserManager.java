package com.toob.qa.gorest.manager;


import com.toob.qa.gorest.model.User;
import com.toob.qabase.http.AbstractHttpTest;
import com.toob.qabase.http.RestClient;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j
@Service
public class UserManager extends AbstractHttpTest {

    public static final String URL_USERS = "/users";
    public static final String TEMPLATE_USERS_SLASH_ID = "%s/%d";
    public static final String TEMPLATE_USERS = "%s";


    public User save(User user) {
        assertNull(user.getId(), "[ user.id ] has to be null");
        Response response = RestClient.post(String.format(TEMPLATE_USERS, URL_USERS), user);
        created( response);
        return response.as(User.class);
    }

    public User fetchById(long id) {
        Response response = RestClient.get(String.format(TEMPLATE_USERS_SLASH_ID, URL_USERS, id));
        allOkay( response);
        return response.as(User.class);
    }

    public User udpate(User user) {
        assertNotNull(user.getId(), "[ user.id ] is required");
        Response response = RestClient.put(String.format(TEMPLATE_USERS_SLASH_ID, URL_USERS, user.getId()), user);
        allOkay( response);
        return response.as(User.class);
    }

    public void delete(long id) {
        Response response = RestClient.delete(String.format(TEMPLATE_USERS_SLASH_ID, URL_USERS, id));
        allOkayWithoutContent( response);
    }


}
