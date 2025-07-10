package com.example.recipeapp.domain.like.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class LikeResponseDto {
    private Long recipeId;
    private long likesCount;
    private boolean liked;
}