package com.example.recipeapp.domain.auth.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SaveUserDto {

    private String nickname;

    private String username;

    private String email;

    private String password;

}
