package com.daesoo.dmotools.gatcha.dto;

import java.util.ArrayList;
import java.util.List;

import com.daesoo.dmotools.common.entity.Gatcha;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GatchaResponseDto {

	private Long id;
	
	private String name;
	
	private int ticketCost;
	
	private int maxDrawCount;
	
	private String type;
	
	private String category;
	
	private String engName;
	
	private String engCategory;
    
    private List<GachaItemResponseDto> gachaItems = new ArrayList<>();
    
    public static GatchaResponseDto of(Gatcha gatcha) {
    	return GatchaResponseDto.builder()
    			.id(gatcha.getId())
    			.name(gatcha.getName())
    			.ticketCost(gatcha.getTicketCost())
    			.maxDrawCount(gatcha.getMaxDrawCount())
    			.engName(gatcha.getEngName())
    			.gachaItems(gatcha.getGatchaItems().stream().map(GachaItemResponseDto::of).toList())
    			.type(gatcha.getType())
    			.category(gatcha.getCategory())
    			.engCategory(gatcha.getEngCategory())
    			.build();
    }
	
}
