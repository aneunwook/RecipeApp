package com.example.recipeapp.domain.like.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class LikeResponseDto {
    private Long recipeId;
    private long likesCount;
    private boolean liked;
}