package com.daesoo.dmotools.seal.dto.response;

import com.daesoo.dmotools.common.entity.Seal;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SealResponseDto {
	

	private Long id;
	
	private String name;
	
	private String engName;
	
	private Integer maxIncrease;
	
	private String statType;
	
	private boolean buyable;
	
	private int masterCount;
	
    
    public static SealResponseDto of(Seal seal) {
    	return SealResponseDto.builder()
    			.id(seal.getId())
    			.name(seal.getName())
    			.engName(seal.getEngName())
    			.maxIncrease(seal.getMaxIncrease())
    			.statType(seal.getStatType().name())
    			.buyable(seal.isBuyable())
    			.masterCount(seal.getMasterCount())
    			.build();
    }

}
