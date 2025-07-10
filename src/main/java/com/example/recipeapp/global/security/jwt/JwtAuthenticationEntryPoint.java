package com.example.recipeapp.global.security.jwt;

import com.example.recipeapp.global.exception.ErrorCode;
import com.example.recipeapp.global.exception.ErrorResponseDto;
import com.example.recipeapp.global.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException authException
    ) throws IOException {

        log.warn("AuthenticationEntryPoint - 인증 실패: {}", authException.getMessage());

        ErrorCode errorCode = ErrorCode.INVALID_TOKEN;
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(errorCode);

        response.setStatus(errorCode.getStatus().value());
        response.setContentType("application/json;charset=UTF-8");

        String json = objectMapper.writeValueAsString(ApiResponse.fail(errorResponseDto.getMessage(), errorResponseDto));
        response.getWriter().write(json);
    }
}
