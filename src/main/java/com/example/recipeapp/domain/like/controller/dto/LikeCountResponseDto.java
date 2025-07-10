package com.example.recipeapp.domain.like.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LikeCountResponseDto {
    private Long recipeId;
    private Long likesCount;
}
