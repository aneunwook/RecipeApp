package com.example.recipeapp.domain.user.controller;

import com.example.recipeapp.domain.auth.domain.model.AuthUser;
import com.example.recipeapp.domain.user.controller.dto.request.PasswordChangeRequestDto;
import com.example.recipeapp.domain.user.controller.dto.response.UserProfileResponseDto;
import com.example.recipeapp.domain.user.controller.dto.response.UserRecipeResponseDto;
import com.example.recipeapp.domain.user.controller.dto.response.UserResponseDto;
import com.example.recipeapp.domain.user.service.UserService;
import com.example.recipeapp.domain.user.service.dto.ChangePasswordDto;
import com.example.recipeapp.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<UserResponseDto>>> getAllUsers(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){

        Page<UserResponseDto> users = userService.getAllUsers(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("회원 전체 조회에 성공했습니다.", users));
    }

    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @Valid @RequestBody PasswordChangeRequestDto request,
            @AuthenticationPrincipal AuthUser authuser){

        ChangePasswordDto change = PasswordChangeRequestDto.toChangePasswordDto(request);
        userService.changePassword(authuser, change);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("비밀번호 변경에 성공했습니다.", null));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponseDto>> getCurrentUser(@AuthenticationPrincipal AuthUser authuser){

        UserProfileResponseDto users = userService.getCurrentUser(authuser);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("내 정보를 조회에 성공했습니다.", users));
    }

    @GetMapping("/me/recipes")
    public ResponseEntity<ApiResponse<Page<UserRecipeResponseDto>>> getCurrentUserRecipe(
            @AuthenticationPrincipal AuthUser authuser,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){

        Page<UserRecipeResponseDto> users = userService.getCurrentUserRecipe(authuser, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("내 레시피 목록 조회에 성공했습니다.", users));
    }
}
