package com.example.recipeapp.domain.user.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserProfileQueryDto {

    private int recipeCount;
    private int totalLikes;
    private Long likedRecipeCount;

}
