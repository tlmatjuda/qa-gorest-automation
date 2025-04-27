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
public class Comment {

    private Long id;

    @JsonProperty("post_id")
    private Long postId;
    private String name;
    private String email;
    private String body;

}
