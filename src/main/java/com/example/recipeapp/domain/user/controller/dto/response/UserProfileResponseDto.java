package com.example.recipeapp.domain.user.controller.dto.response;

import com.example.recipeapp.domain.user.domain.model.User;
import com.example.recipeapp.domain.user.service.dto.UserProfileDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponseDto {

    private Long id;
    private String nickname;
    private String username;
    private String email;
    private int recipeCount;
    private int totalLikes;
    private Long likedRecipeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserProfileResponseDto from(User user, UserProfileDto userProfileDto){
        return UserProfileResponseDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .username(user.getUsername())
                .email(user.getEmail())
                .recipeCount(userProfileDto.getRecipeCount())
                .totalLikes(userProfileDto.getTotalLikes())
                .likedRecipeCount(userProfileDto.getLikedRecipeCount())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
