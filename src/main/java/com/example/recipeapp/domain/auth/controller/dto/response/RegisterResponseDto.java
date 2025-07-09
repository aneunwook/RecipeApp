package com.example.recipeapp.domain.auth.controller.dto.response;

import com.example.recipeapp.domain.user.domain.model.User;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RegisterResponseDto {

    private Long id;
    private String username;
    private String email;
    private String role;
    private LocalDateTime createdAt;

    public static RegisterResponseDto from(User user) {
        return RegisterResponseDto.builder()
                                  .id(user.getId())
                                  .username(user.getUsername())
                                  .email(user.getEmail())
                                  .role(user.getRole().getRole())
                                  .createdAt(user.getCreatedAt())
                                  .build();
    }

}
