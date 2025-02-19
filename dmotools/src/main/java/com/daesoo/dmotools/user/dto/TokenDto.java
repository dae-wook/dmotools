package com.daesoo.dmotools.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDto {

    
    private String accessToken;
    
    private String refreshToken;
    
    public static TokenDto of( String accessToken, String refreshToken) {
    	return TokenDto.builder()
    			.accessToken(accessToken)
    			.refreshToken(refreshToken)
    			.build();
    }
	
}
