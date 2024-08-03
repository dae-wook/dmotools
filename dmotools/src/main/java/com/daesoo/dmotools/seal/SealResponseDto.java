package com.daesoo.dmotools.seal;

import com.daesoo.dmotools.common.entity.Seal;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SealResponseDto {
	

	private Long id;
	
	private String name;
	
	private Integer maxIncrease;
	
    private String statType;
    
    public static SealResponseDto of(Seal seal) {
    	return SealResponseDto.builder()
    			.id(seal.getId())
    			.name(seal.getName())
    			.maxIncrease(seal.getMaxIncrease())
    			.statType(seal.getStatType().name())
    			.build();
    }

}
