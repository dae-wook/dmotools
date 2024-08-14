package com.daesoo.dmotools.raid;



import java.time.LocalDateTime;

import com.daesoo.dmotools.common.entity.Timer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TimerResponseDto {
	
	private Long id;
	
	private Long clientId;
	
	private LocalDateTime startAt;
	
	private int channel;
	
	private int voteCount;
	
	private String server;
	
	private Long raidId;
	
	public static TimerResponseDto of(Timer timer) {
		return TimerResponseDto.builder()
				.id(timer.getId())
				.clientId(timer.getClientId())
				.startAt(timer.getStartAt())
				.channel(timer.getChannel())
				.voteCount(timer.getVoteCount())
				.server(timer.getServer().name())
				.raidId(timer.getRaid().getId())
				.build();
	}
	
}
