package com.daesoo.dmotools.raid;



import java.time.LocalDateTime;

import com.daesoo.dmotools.common.entity.Timer;
import com.daesoo.dmotools.user.dto.UserResponseDto;

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
	
	private UserResponseDto user;
	
	public static TimerResponseDto of(Timer timer) {
		return TimerResponseDto.builder()
				.id(timer.getId())
				.clientId(timer.getClientId())
				.startAt(timer.getStartAt())
				.channel(timer.getChannel())
				.voteCount(timer.getVoteCount())
				.server(timer.getServer().name())
				.raidId(timer.getRaid().getId())
				.user(timer.getUser() != null ? UserResponseDto.of(timer.getUser(), null, null) : null)
				.build();
	}
	
}
