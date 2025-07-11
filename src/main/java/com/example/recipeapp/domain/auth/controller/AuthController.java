package com.example.recipeapp.domain.auth.controller;

import com.example.recipeapp.domain.auth.controller.dto.request.LoginRequestDto;
import com.example.recipeapp.domain.auth.controller.dto.request.RegisterRequestDto;
import com.example.recipeapp.domain.auth.controller.dto.request.WithdrawRequestDto;
import com.example.recipeapp.domain.auth.controller.dto.response.LoginResponseDto;
import com.example.recipeapp.domain.auth.domain.model.AuthUser;
import com.example.recipeapp.domain.auth.service.dto.request.DeleteUserDto;
import com.example.recipeapp.domain.auth.service.dto.request.SaveUserDto;
import com.example.recipeapp.domain.auth.controller.dto.response.RegisterResponseDto;
import com.example.recipeapp.domain.auth.service.AuthService;
import com.example.recipeapp.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
        RegisterResponseDto registerResponseDto = authService.register(saveUserDto);
        ApiResponse<RegisterResponseDto> response = ApiResponse.success("회원가입이 완료되었습니다.", registerResponseDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(
        @Valid @RequestBody LoginRequestDto loginRequestDto) {

        LoginResponseDto loginResponseDto = authService.login(loginRequestDto);
        ApiResponse<LoginResponseDto> response = ApiResponse.success("로그인에 성공했습니다.", loginResponseDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponse<Void>> withdraw(
        @AuthenticationPrincipal AuthUser authUser,
        @RequestBody @Valid WithdrawRequestDto withdrawRequestDto
    ) {

        DeleteUserDto deleteUserDto = new DeleteUserDto(
            authUser.getId(),
            withdrawRequestDto.getPassword()
        );
        authService.withdraw(deleteUserDto);
        ApiResponse<Void> response = ApiResponse.success("회원 탈퇴 처리되었습니다.", null);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



}
