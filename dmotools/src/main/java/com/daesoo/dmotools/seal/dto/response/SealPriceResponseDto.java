package com.daesoo.dmotools.seal.dto.response;

import java.time.LocalDateTime;

import com.daesoo.dmotools.common.entity.SealPrice;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SealPriceResponseDto {
	

	private Long id;
	
	private double price;
	
	private int regCount;
	
	private Long sealId;
	
	private String server;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime modifiedAt;
    
    public static SealPriceResponseDto of(SealPrice sealPrice) {
    	return SealPriceResponseDto.builder()
    			.id(sealPrice.getId())
    			.price(sealPrice.getPrice())
    			.regCount(sealPrice.getRegCount())
    			.sealId(sealPrice.getSeal().getId())
    			.server(sealPrice.getServerType() != null ? sealPrice.getServerType().name() : null)
    			.createdAt(sealPrice.getCreatedAt())
    			.modifiedAt(sealPrice.getModifiedAt())
    			.build();
    }

}
