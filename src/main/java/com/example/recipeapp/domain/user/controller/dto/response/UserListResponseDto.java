package com.example.recipeapp.domain.user.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserListResponseDto {

    private List<UserResponseDto> content;
    private long totalElements;
    private int totalPages;
    private int size;
    private int number;

    public static UserListResponseDto from(Page<UserResponseDto> pageData) {
        return UserListResponseDto.builder()
                .content(pageData.getContent())
                .totalElements(pageData.getTotalElements())
                .totalPages(pageData.getTotalPages())
                .size(pageData.getSize())
                .number(pageData.getNumber())
                .build();
    }
}
