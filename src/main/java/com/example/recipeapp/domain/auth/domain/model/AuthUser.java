package com.example.recipeapp.domain.auth.domain.model;

import com.example.recipeapp.domain.user.domain.model.UserRoleEnum;
import lombok.Getter;

@Getter
public class AuthUser {

    private final Long id;
    private final String nickname;
    private final String username;
    private final String email;
    private final UserRoleEnum userRole;

    public AuthUser(Long id, String nickname, String username, String email,  UserRoleEnum userRole) {
        this.id = id;
        this.nickname = nickname;
        this.username = username;
        this.email = email;
        this.userRole = userRole;
    }
}
