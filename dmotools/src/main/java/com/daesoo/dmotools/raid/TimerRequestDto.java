package com.daesoo.dmotools.raid;



import java.time.LocalDateTime;

import com.daesoo.dmotools.common.dto.ServerType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TimerRequestDto {
	
	private int timeRemaining;
	
	private int channel;
	
	private int voteCount;
	
	private ServerType server;
	
	private Long clientId;
	
	public LocalDateTime getStartAt() {
		return LocalDateTime.now().plusMinutes(this.timeRemaining);
	}
	
}
