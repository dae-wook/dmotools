package com.daesoo.dmotools.seal.dto.response;

import com.daesoo.dmotools.common.entity.UserPrice;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserPriceResponseDto {
    

	
	private Long id;
	
    private Double price;
	
	private Long userId;
    
    public static UserPriceResponseDto of(UserPrice userPrice) {
    	return UserPriceResponseDto.builder()
    			.price(userPrice.getCustomPrice())
    			.id(userPrice.getSeal().getId())
    			.userId(userPrice.getUser().getId())
    			.build();
    }

}
