package com.daesoo.dmotools.gatcha.dto;



import com.daesoo.dmotools.common.entity.GachaItem;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GachaItemResponseDto {
	
	private Long id;
	
	private Integer rarity;
	
	private Float probability;
	
	private ItemResponseDto item;
	
	
	public static GachaItemResponseDto of(GachaItem gatchaItem) {
		return GachaItemResponseDto.builder()
				.id(gatchaItem.getId())
				.item(ItemResponseDto.of(gatchaItem.getItem()))
				.probability(gatchaItem.getProbability())
				.rarity(gatchaItem.getRarity())
				.build();
	}
}
