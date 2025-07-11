package com.example.recipeapp.domain.user.service;

import com.example.recipeapp.domain.auth.domain.model.AuthUser;
import com.example.recipeapp.domain.user.controller.dto.response.UserProfileResponseDto;
import com.example.recipeapp.domain.user.controller.dto.response.UserRecipeResponseDto;
import com.example.recipeapp.domain.user.controller.dto.response.UserResponseDto;
import com.example.recipeapp.domain.user.domain.dto.UserProfileQueryDto;
import com.example.recipeapp.domain.user.domain.dto.UserRecipeQueryDto;
import com.example.recipeapp.domain.user.domain.model.User;
import com.example.recipeapp.domain.user.domain.repository.UserQueryRepositoryImpl;
import com.example.recipeapp.domain.user.domain.repository.UserRepository;
import com.example.recipeapp.domain.user.service.dto.ChangePasswordDto;
import com.example.recipeapp.domain.user.service.dto.UserProfileDto;
import com.example.recipeapp.global.exception.CustomException;
import com.example.recipeapp.global.exception.ErrorCode;
import com.example.recipeapp.global.security.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserQueryRepositoryImpl userQueryRepository;

    @Transactional(readOnly = true)
    public Page<UserResponseDto> getAllUsers(Pageable pageable) {

        Page<User> users = userRepository.findAllByIsDeletedFalse(pageable);

        List<UserResponseDto> userResponseDtos = users.stream()
                                                .map(UserResponseDto::from)
                                                .toList();

        return new PageImpl<>(userResponseDtos, pageable, users.getTotalElements());
    }

    @Transactional
    public void changePassword(AuthUser authuser, ChangePasswordDto request) {
        User user = findByIdAndIsDeletedFalseOrThrow(authuser.getId());

        // 현재 비밀번호가 일치하지 않는 경우
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        // 새 비밀번호가 기존 비밀번호와 같은 경우
        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())){
            throw new CustomException(ErrorCode.NEW_PASSWORD_SAME);
        }

        // 새 비밀번호와 비밀번호 확인이 일치하지 않는 경우
        if (!request.getNewPassword().equals(request.getConfirmPassword())){
            throw new CustomException((ErrorCode.PASSWORD_CONFIRM_MISMATCH));
        }

        String newPassword = passwordEncoder.encode(request.getNewPassword());

        user.changePassword(newPassword);
    }

    public UserProfileResponseDto getCurrentUser(AuthUser authuser) {
        User user = findByIdAndIsDeletedFalseOrThrow(authuser.getId());

        UserProfileQueryDto queryDto = userQueryRepository.getUserInfo(user.getId());

        UserProfileDto userProfile = UserProfileDto.from(queryDto);

        return UserProfileResponseDto.from(user, userProfile);
    }

    public Page<UserRecipeResponseDto> getCurrentUserRecipe(AuthUser authuser, Pageable pageable) {
        User user = findByIdAndIsDeletedFalseOrThrow(authuser.getId());

        Page<UserRecipeQueryDto> queryDto = userQueryRepository.findRecipesByUserId(user.getId(), pageable);

        return queryDto.map(UserRecipeResponseDto::from);
    }

    public User findByIdAndIsDeletedFalseOrThrow(Long userId) {
        return userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
