//package com.example.recipeapp.domain.like.controller;
//
//import com.example.recipeapp.domain.like.Service.LikeService;
//import com.example.recipeapp.domain.like.controller.dto.LikeResponseDto;
//import com.example.recipeapp.domain.user.domain.model.User;
//import com.example.recipeapp.global.response.ApiResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequiredArgsConstructor
//public class LikeController {
//
//    private final LikeService likeService;
//
//    //좋아요 등록
//    @PostMapping
//    public ResponseEntity<ApiResponse<LikeResponseDto>> registerLike(
//            @RequestParam Long recipeId,
//            @AuthenticationPrincipal User user) {
//
//        LikeResponseDto response = likeService.registerLike(user.getId(), recipeId);
//        return ResponseEntity.ok(ApiResponse.success("좋아요 등록 완료", response));
//    }
//
//    // 좋아요 취소
//    @DeleteMapping
//    public ResponseEntity<ApiResponse<LikeResponseDto>> cancelLike(
//            @RequestParam Long recipeId,
//            @AuthenticationPrincipal User user) {
//
//        LikeResponseDto response = likeService.cancelLike(user.getId(), recipeId);
//        return ResponseEntity.ok(ApiResponse.success("좋아요 취소 완료", response));
//    }
//
//    // 좋아요 개수 조회 (익명 가능)
//    @GetMapping("/count")
//    public ResponseEntity<ApiResponse<LikeCountResponseDto>> countLikes(
//            @RequestParam Long recipeId) {
//
//        LikeCountResponseDto response = likeService.countLikes(recipeId);
//        return ResponseEntity.ok(ApiResponse.success("좋아요 개수 조회 성공", response));
//    }
//
//    // 좋아요 여부 확인 (로그인 사용자)
//    @GetMapping("/status")
//    public ResponseEntity<ApiResponse<LikeStatusResponseDto>> checkLiked(
//            @RequestParam Long recipeId,
//            @AuthenticationPrincipal User user) {
//
//        LikeStatusResponseDto response = likeService.isLiked(user.getId(), recipeId);
//        return ResponseEntity.ok(ApiResponse.success("좋아요 여부 조회 성공", response));
//    }
//
//
//}
