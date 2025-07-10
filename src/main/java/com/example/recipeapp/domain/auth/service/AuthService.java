package com.example.recipeapp.domain.auth.service;

import com.example.recipeapp.domain.auth.controller.dto.request.LoginRequestDto;
import com.example.recipeapp.domain.auth.controller.dto.response.LoginResponseDto;
import com.example.recipeapp.domain.auth.service.dto.request.SaveUserDto;
import com.example.recipeapp.domain.auth.controller.dto.response.RegisterResponseDto;
import com.example.recipeapp.domain.user.domain.model.User;
import com.example.recipeapp.domain.user.domain.repository.UserRepository;
import com.example.recipeapp.global.exception.CustomException;
import com.example.recipeapp.global.exception.ErrorCode;
import com.example.recipeapp.global.security.PasswordEncoder;
import com.example.recipeapp.global.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public RegisterResponseDto register(SaveUserDto saveUserDto) {

        checkDuplicatesOrThrow(saveUserDto.getNickname(), saveUserDto.getEmail());

        User user = User.builder()
                        .nickname(saveUserDto.getNickname())
                        .username(saveUserDto.getUsername())
                        .email(saveUserDto.getEmail())
                        .password(passwordEncoder.encode(saveUserDto.getPassword()))
                        .build();

        User savedUser = userRepository.save(user);

        return RegisterResponseDto.from(savedUser);
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        User loginUser = userRepository.findByNicknameAndIsDeletedFalse(loginRequestDto.getNickname())
                                       .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), loginUser.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        String token = jwtUtil.createToken(
            loginUser.getId(),
            loginUser.getNickname(),
            loginUser.getUsername(),
            loginUser.getEmail(),
            loginUser.getRole()
        );

        return new LoginResponseDto(token);
    }

    private void checkDuplicatesOrThrow(String nickname, String email) {

        boolean hasDuplicateNickname = userRepository.existsByNickname(nickname);
        if (hasDuplicateNickname) {
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }

        boolean hasDuplicateEmail = userRepository.existsByEmail(email);
        if (hasDuplicateEmail) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }
    }

}
