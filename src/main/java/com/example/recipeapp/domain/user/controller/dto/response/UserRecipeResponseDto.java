package com.example.recipeapp.domain.user.controller.dto.response;

import com.example.recipeapp.domain.recipes.domain.model.RecipeCategory;
import com.example.recipeapp.domain.user.domain.dto.UserRecipeQueryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class UserRecipeResponseDto {

    private final Long userId;
    private final Long recipeId;
    private final String title;
    private final RecipeCategory category;
    private final String imageUrl;
    private final int likes;
    private final LocalDateTime createdAt;

    public static UserRecipeResponseDto from(UserRecipeQueryDto queryDto){
        return UserRecipeResponseDto.builder()
                .userId(queryDto.getUserId())
                .recipeId(queryDto.getRecipeId())
                .title(queryDto.getTitle())
                .category(queryDto.getCategory())
                .imageUrl(queryDto.getImageUrl())
                .likes(queryDto.getLikes())
                .createdAt(queryDto.getCreatedAt())
                .build();
    }
}
