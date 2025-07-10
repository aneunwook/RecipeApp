package com.example.recipeapp.domain.user.controller;

import com.example.recipeapp.domain.user.controller.dto.PasswordChangeRequest;
import com.example.recipeapp.domain.user.controller.dto.response.UserResponseDto;
import com.example.recipeapp.domain.user.service.UserService;
import com.example.recipeapp.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
