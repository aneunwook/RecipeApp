package com.example.recipeapp.domain.user.domain.repository;

import com.example.recipeapp.domain.user.domain.dto.UserProfileQueryDto;
import com.example.recipeapp.domain.user.domain.dto.UserRecipeQueryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserQueryRepository {
    /**
     *  사용자 관련 조회 전용 쿼리 인터페이스
     * - 사용자 프로필 요약 정보
     * - 사용자가 작성한 레시피 목록
     */
    UserProfileQueryDto getUserInfo(Long userId);

    Page<UserRecipeQueryDto> findRecipesByUserId(Long userId, Pageable pageable);
}
