package com.daesoo.dmotools.seal.dto.response;

import java.util.List;

import com.daesoo.dmotools.seal.dto.SealPriceHistoryDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SealPriceHistoryResponseDto {
	

	private Long sealId;
	
	private List<SealPriceHistoryDto> hisotry;
    
    public static SealPriceHistoryResponseDto of(Long sealId, List<SealPriceHistoryDto> history) {
    	return SealPriceHistoryResponseDto.builder()
    			.sealId(sealId)
    			.hisotry(history)
    			.build();
    }

}
