package com.example.recipeapp.domain.like.controller;

import com.example.recipeapp.domain.like.Service.LikeService;
import com.example.recipeapp.domain.like.controller.dto.LikeCountResponseDto;
import com.example.recipeapp.domain.like.controller.dto.LikeResponseDto;
import com.example.recipeapp.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {

    private final LikeService likeService;

    //좋아요 등록
    @PostMapping("/recipes/{recipeId}/likes")
    public ResponseEntity<ApiResponse<LikeResponseDto>> registerLike(
            @PathVariable Long recipeId,
            @AuthenticationPrincipal User user) {  //로그인된 사용자 정보

        LikeResponseDto response = likeService.registerLike(user.getId(), recipeId);
        return ResponseEntity.ok(ApiResponse.success("좋아요 등록 완료", response));
    }

    // 좋아요 취소
    @DeleteMapping("/recipes/{recipeId}/likes")
    public ResponseEntity<ApiResponse<LikeResponseDto>> cancelLike(
            @PathVariable Long recipeId,
            @AuthenticationPrincipal User user) {

        LikeResponseDto response = likeService.cancelLike(user.getId(), recipeId);
        return ResponseEntity.ok(ApiResponse.success("좋아요 취소 완료", response));
    }

    // 좋아요 개수 조회 (로그인 사용자만 가능)
    @GetMapping("/recipes/{recipeId}/likes/count")
    public ResponseEntity<ApiResponse<LikeCountResponseDto>> countLikes(
            @PathVariable Long recipeId,
            @AuthenticationPrincipal User user) {

        LikeCountResponseDto response = likeService.countLikes(recipeId);
        return ResponseEntity.ok(ApiResponse.success("좋아요 개수 조회 성공", response));
    }


}
