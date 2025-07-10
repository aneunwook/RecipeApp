package com.example.recipeapp.domain.like.domain.model.entity.test;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeResponseDto {
    private boolean liked;
    private Long likeCount;
}
