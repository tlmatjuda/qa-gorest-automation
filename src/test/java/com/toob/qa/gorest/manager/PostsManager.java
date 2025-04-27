package com.toob.qa.gorest.manager;


import com.toob.qa.gorest.model.Post;
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
public class PostsManager extends AbstractManager {

    public static final String URL_POSTS = "/posts";


    public Post save(Post post) {
        assertNull(post.getId(), "[ post.id ] has to be null");
        Response response = RestClient.post(String.format(TEMPLATE_ENTITY_PATH, URL_POSTS), post);
        HttpSupport.created( response);
        return response.as(Post.class);
    }

    public Post fetchById(long id) {
        Response response = RestClient.get(String.format(TEMPLATE_ENTITY_SLASH_ID_PATH, URL_POSTS, id));
        HttpSupport.allOkay( response);
        return response.as(Post.class);
    }

    public Post udpate(Post post) {
        assertNotNull(post.getId(), "[ post.id ] is required");
        Response response = RestClient.put(String.format(TEMPLATE_ENTITY_SLASH_ID_PATH, URL_POSTS, post.getId()), post);
        HttpSupport.allOkay( response);
        return response.as(Post.class);
    }

    public void delete(long id) {
        Response response = RestClient.delete(String.format(TEMPLATE_ENTITY_SLASH_ID_PATH, URL_POSTS, id));
        HttpSupport.allOkayWithoutContent( response);
    }


}
