package com.daesoo.dmotools.seal.dto;

import java.time.LocalDateTime;

import com.daesoo.dmotools.common.entity.SealPrice;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SealPriceHistoryDto {
	
    private Long id;
    private double price;
    private LocalDateTime createdAt;
    
    public static SealPriceHistoryDto create(SealPrice sealPrice) {
    	return SealPriceHistoryDto.builder()
    		.id(sealPrice.getId())
    		.price(sealPrice.getPrice())
    		.createdAt(sealPrice.getCreatedAt())
    		.build();
    }

}
