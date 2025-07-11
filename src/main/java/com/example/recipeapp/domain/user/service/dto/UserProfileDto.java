package com.example.recipeapp.domain.user.service.dto;

import com.example.recipeapp.domain.user.domain.dto.UserProfileQueryDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileDto {

    private int recipeCount;
    private int totalLikes;
    private int likedRecipeCount;

    public static UserProfileDto from(UserProfileQueryDto queryDto){
        return UserProfileDto.builder()
                .recipeCount(queryDto.getRecipeCount())
                .totalLikes(queryDto.getTotalLikes())
                .likedRecipeCount(queryDto.getLikedRecipeCount())
                .build();
    }
}
