package com.daesoo.dmotools.gatcha.dto;



import com.daesoo.dmotools.common.entity.Item;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemResponseDto {
	
	private Long id;
	
	private String name;
	
	public static ItemResponseDto of(Item item) {
		return ItemResponseDto.builder()
				.id(item.getId())
				.name(item.getName())
				.build();
	}
}
