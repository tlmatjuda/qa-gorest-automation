package com.toob.qa.gorest.factory;

import com.toob.qa.gorest.model.User;

import java.util.UUID;

public class UserFactory {

    public static User randomUser() {
        return User.builder()
                .name("QA BAse " + UUID.randomUUID())
                .email("qabase" + UUID.randomUUID() + "@toobprojects.com")
                .gender("male")
                .status("active")
                .build();
    }

}
