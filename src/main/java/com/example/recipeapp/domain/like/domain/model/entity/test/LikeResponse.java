package com.example.recipeapp.domain.like.domain.model.entity.test;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LikeResponse {
    private Long recipeId;
    private int totalLikes;
}
