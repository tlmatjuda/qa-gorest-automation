package com.toob.qa.gorest.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private long id;

    @JsonProperty("user_id")
    private Long userId;
    private String title;
    private String body;
}
