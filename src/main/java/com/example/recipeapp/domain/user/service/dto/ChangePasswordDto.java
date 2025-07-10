package com.example.recipeapp.domain.user.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChangePasswordDto {

    private String currentPassword;

    private String newPassword;

    private String confirmPassword;

}
