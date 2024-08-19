package com.daesoo.dmotools.raid;

import java.util.ArrayList;
import java.util.List;

import com.daesoo.dmotools.common.entity.Raid;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RaidResponseDto {
	private Long id;
	
	private String name;
	
	private String location;
	
	private String channels;
    
    private List<TimerResponseDto> times = new ArrayList<>();
    
    public static RaidResponseDto of(Raid raid) {
    	return RaidResponseDto.builder()
    			.id(raid.getId())
    			.name(raid.getName())
    			.location(raid.getLocation())
    			.channels(raid.getChannels())
//    			.times(raid.getTimers().stream().map(TimerResponseDto::of).toList())
    			.build();
    }
    
    public void setTimer(List<TimerResponseDto> timers) {
    	this.times = timers;
    }
}
