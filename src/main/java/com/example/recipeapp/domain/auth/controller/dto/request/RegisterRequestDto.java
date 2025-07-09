package com.example.recipeapp.domain.auth.controller.dto.request;

import com.example.recipeapp.domain.auth.service.dto.request.SaveUserDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class RegisterRequestDto {

    @NotBlank
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,10}$", message = "닉네임은 2-10자로 한글, 영어, 숫자로 조합되어야 합니다.")
    private String nickname;

    @NotBlank
    @Pattern(regexp = "^[가-힣a-zA-Z]{2,10}$", message = "이름은 2-10자의 한글 또는 영문을 입력해주세요.")
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "비밀번호는 8-15자로 영문과 숫자로 조합되어야 합니다.")
    private String password;

    public static SaveUserDto toSaveUserDto(RegisterRequestDto registerRequestDto) {
        return SaveUserDto.builder()
                          .nickname(registerRequestDto.getNickname())
                          .username(registerRequestDto.getUsername())
                          .email(registerRequestDto.getEmail())
                          .password(registerRequestDto.getPassword())
                          .build();
    }


}

