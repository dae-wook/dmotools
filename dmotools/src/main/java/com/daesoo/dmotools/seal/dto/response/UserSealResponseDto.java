package com.daesoo.dmotools.seal.dto.response;

import com.daesoo.dmotools.common.entity.UserSeal;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSealResponseDto {
    

	
	private Long id;
	
    private Integer count;			
	
	private Long userId;
    
    public static UserSealResponseDto of(UserSeal inventory) {
    	return UserSealResponseDto.builder()
    			.count(inventory.getCount())
    			.id(inventory.getSeal().getId())
    			.userId(inventory.getUser().getId())
    			.build();
    }

}
