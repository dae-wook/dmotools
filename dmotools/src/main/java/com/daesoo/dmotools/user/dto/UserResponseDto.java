package com.daesoo.dmotools.user.dto;

import com.daesoo.dmotools.common.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {

	private Long id;
	
    private String email;

    private String nickname;
    
    private String accessToken;
    
    private String refreshToken;
    
    private int timerCompleteCount;
    
    public static UserResponseDto of(User user, String accessToken, String refreshToken) {
    	return UserResponseDto.builder()
    			.id(user.getId())
    			.email(user.getEmail())
    			.nickname(user.getNickname())
    			.accessToken(accessToken)
    			.refreshToken(refreshToken)
    			.timerCompleteCount(user.getTimerCompleteCount())
    			.build();
    }
	
}
