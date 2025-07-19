package com.example.recipeapp.domain.auth.service;

import com.example.recipeapp.domain.auth.controller.dto.request.RegisterRequestDto;
import com.example.recipeapp.domain.auth.controller.dto.response.RegisterResponseDto;
import com.example.recipeapp.domain.auth.service.dto.request.SaveUserDto;
import com.example.recipeapp.domain.user.domain.model.User;
import com.example.recipeapp.domain.user.domain.repository.UserRepository;
import com.example.recipeapp.global.security.PasswordEncoder;
import com.example.recipeapp.global.security.jwt.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Test
    void 회원가입을_성공한다() {
        // given
        RegisterRequestDto dto = new RegisterRequestDto("테스트닉네임", "홍길동", "test@email.com", "password1");

        SaveUserDto saveUserDto = RegisterRequestDto.toSaveUserDto(dto);

        when(passwordEncoder.encode(anyString())).thenReturn("암호비번");
        User fakeUser = User.builder()
                .nickname("테스트닉네임")
                .username("홍길동")
                .email("test@email.com")
                .password("암호비번")
                .build();
        when(userRepository.save(any(User.class))).thenReturn(fakeUser);

        // when
        RegisterResponseDto result = authService.register(saveUserDto);

        // then
        assertThat(result.getNickname()).isEqualTo("테스트닉네임");
        assertThat(result.getEmail()).isEqualTo("test@email.com");
    }
}