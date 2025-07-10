package com.example.recipeapp.domain.like.domain.model.entity.test;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recipes/{recipeId}/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<LikeResponse> like(@RequestHeader("X-USER-ID") Long userId,
                                             @PathVariable Long recipeId) {
        LikeResponse res = likeService.likeRecipe(userId, recipeId);
        return ResponseEntity.ok(res);
    }
}
