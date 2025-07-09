package com.example.recipeapp.domain.auth.controller;

import com.example.recipeapp.domain.auth.controller.dto.request.RegisterRequestDto;
import com.example.recipeapp.domain.auth.service.dto.request.SaveUserDto;
import com.example.recipeapp.domain.auth.controller.dto.response.RegisterResponseDto;
import com.example.recipeapp.domain.auth.service.AuthService;
import com.example.recipeapp.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponseDto>> register(
        @Valid @RequestBody RegisterRequestDto registerRequestDto) {

        SaveUserDto saveUserDto = RegisterRequestDto.toSaveUserDto(registerRequestDto);
        RegisterResponseDto responseDto = authService.register(saveUserDto);
        ApiResponse<RegisterResponseDto> response = ApiResponse.success("회원가입이 완료되었습니다.", responseDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
