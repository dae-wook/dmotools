package com.daesoo.dmotools.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDto {
	
    private String idToken;

    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,10}$", message = "잘못된 양식의 닉네임")
    private String nickname;
	
}
