package com.example.recipeapp.domain.auth.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeleteUserDto {

    private final Long userId;
    private final String password;

    public DeleteUserDto(Long userId, String password) {
        this.userId = userId;
        this.password = password;
    }

}
