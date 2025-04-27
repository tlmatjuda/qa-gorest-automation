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
public class Todo {

    private Long id;

    @JsonProperty("user_id")
    private Long userId;
    private String title;

    @JsonProperty("due_on")
    private String dueOn;
    private String status;

}
