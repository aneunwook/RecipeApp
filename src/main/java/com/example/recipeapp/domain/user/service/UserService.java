package com.example.recipeapp.domain.user.service;

import com.example.recipeapp.domain.user.controller.dto.response.UserListResponseDto;
import com.example.recipeapp.domain.user.controller.dto.response.UserResponseDto;
import com.example.recipeapp.domain.user.domain.model.User;
import com.example.recipeapp.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserListResponseDto getAllUsers(Pageable pageable) {

        Page<User> users = userRepository.findAllByIsDeletedFalse(pageable);

        List<UserResponseDto> userResponseDtos = users.stream()
                                                .map(UserResponseDto::from)
                                                .toList();

        return UserListResponseDto.from(
                new PageImpl<>(userResponseDtos, pageable, users.getTotalElements()));
    }


}
