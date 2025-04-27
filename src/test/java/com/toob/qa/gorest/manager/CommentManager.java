package com.toob.qa.gorest.manager;


import com.toob.qa.gorest.model.Comment;
import com.toob.qabase.http.RestClient;
import com.toob.qabase.http.support.HttpSupport;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j
@Service
public class CommentManager extends AbstractManager {

    public static final String URL_COMMENTS = "/comments";
    public static final String URL_COMMENT_SLASH_POST_SLASH_USER = "/comment/post/user";


    public Comment save(Comment comment) {
        assertNull(comment.getId(), "[ comment.id ] has to be null");
        Response response = RestClient.post(String.format(TEMPLATE_ENTITY_PATH, URL_COMMENTS), comment);
        HttpSupport.created( response);
        return response.as(Comment.class);
    }

    public Comment fetchById(long id) {
        Response response = RestClient.get(String.format(TEMPLATE_ENTITY_SLASH_ID_PATH, URL_COMMENTS, id));
        HttpSupport.allOkay( response);
        return response.as(Comment.class);
    }

    public Comment udpate(Comment comment) {
        assertNotNull(comment.getId(), "[ comment.id ] is required");
        Response response = RestClient.put(String.format(TEMPLATE_ENTITY_SLASH_ID_PATH, URL_COMMENTS, comment.getId()), comment);
        HttpSupport.allOkay( response);
        return response.as(Comment.class);
    }

    public void delete(long id) {
        Response response = RestClient.delete(String.format(TEMPLATE_ENTITY_SLASH_ID_PATH, URL_COMMENTS, id));
        HttpSupport.allOkayWithoutContent( response);
    }


}
