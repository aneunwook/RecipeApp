package com.example.recipeapp.domain.auth.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequestDto {

    @NotBlank
    private String nickname;

    @NotBlank
    private String password;

}
