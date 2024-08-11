package com.daesoo.dmotools.alarm;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class TimerResponseDto {

	private Long raidId;
	private LocalDate remainTime;
	private int voteCount;
	
}
