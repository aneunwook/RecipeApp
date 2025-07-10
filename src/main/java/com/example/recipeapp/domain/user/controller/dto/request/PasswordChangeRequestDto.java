package com.example.recipeapp.domain.user.controller.dto.request;

import com.example.recipeapp.domain.user.service.dto.ChangePasswordDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class PasswordChangeRequestDto {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "비밀번호는 8-15자로 영문과 숫자로 조합되어야 합니다.")
    private String currentPassword;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "비밀번호는 8-15자로 영문과 숫자로 조합되어야 합니다.")
    private String newPassword;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "비밀번호는 8-15자로 영문과 숫자로 조합되어야 합니다.")
    private String confirmPassword;

    public static ChangePasswordDto toChangePasswordDto(PasswordChangeRequestDto request){
        return ChangePasswordDto.builder()
                                .currentPassword(request.getCurrentPassword())
                                .newPassword(request.getNewPassword())
                                .confirmPassword(request.getConfirmPassword())
                                .build();
    }
}
