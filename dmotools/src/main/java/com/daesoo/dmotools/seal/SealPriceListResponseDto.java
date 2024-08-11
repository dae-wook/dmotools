package com.daesoo.dmotools.seal;

import java.time.LocalDateTime;

import com.daesoo.dmotools.common.entity.SealPrice;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SealPriceListResponseDto {
	

	private Long sealId;
	
	private Long id;
	
	private double price;
	
	private int regCount;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime modifiedAt;
    
    public static SealPriceListResponseDto of(SealPrice sealPrice) {
    	return SealPriceListResponseDto.builder()
    			.id(sealPrice.getId())
    			.price(sealPrice.getPrice())
    			.regCount(sealPrice.getRegCount())
    			.sealId(sealPrice.getSeal().getId())
    			.createdAt(sealPrice.getCreatedAt())
    			.modifiedAt(sealPrice.getModifiedAt())
    			.build();
    }

}
