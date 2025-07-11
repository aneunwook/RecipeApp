package com.example.recipeapp.domain.user.domain.repository;

import com.example.recipeapp.domain.user.domain.dto.UserProfileQueryDto;
import com.example.recipeapp.domain.user.domain.dto.UserRecipeQueryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserQueryRepository {

    UserProfileQueryDto getUserInfo(Long userId);

    Page<UserRecipeQueryDto> findRecipesByUserId(Long userId, Pageable pageable);
}
