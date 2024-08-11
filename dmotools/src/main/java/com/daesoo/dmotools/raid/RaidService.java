package com.daesoo.dmotools.raid;

import java.util.List;

import org.springframework.stereotype.Service;

import com.daesoo.dmotools.alarm.AlarmService;
import com.daesoo.dmotools.common.dto.ErrorMessage;
import com.daesoo.dmotools.common.entity.Raid;
import com.daesoo.dmotools.common.entity.Timer;
import com.daesoo.dmotools.common.repository.RaidRepository;
import com.daesoo.dmotools.common.repository.TimerRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RaidService {

	private final RaidRepository raidRepository;
	private final TimerRepository timerRepository;
	private final AlarmService alarmService;

	public List<RaidResponseDto> getRaids() {
		// TODO Auto-generated method stub
		return raidRepository.findAll().stream().map(RaidResponseDto :: of).toList();
	}

	public TimerResponseDto createTimer(Long raidId, TimerRequestDto dto) {

		Raid raid = raidRepository.findById(raidId).orElseThrow(
				() ->  new IllegalArgumentException(ErrorMessage.RAID_NOT_FOUND.getMessage()));
		
		
		Timer timer = Timer.create(dto, raid);
		
//		timerRepository.save(timer)
		TimerResponseDto responseDto = TimerResponseDto.of(timerRepository.save(timer));
		alarmService.notify(responseDto, "time created", "created");
		
		
		
		return responseDto;
	}

	@Transactional
	public TimerResponseDto voteTimer(Long timerId) {
		// TODO Auto-generated method stub
		
		Timer timer = timerRepository.findById(timerId).orElseThrow(
				() ->  new IllegalArgumentException(ErrorMessage.TIMER_NOT_FOUND.getMessage()));
		
		
		timer.increaseVoteCount();
		
		TimerResponseDto responseDto = TimerResponseDto.of(timer);
		alarmService.notify(responseDto, "time voted", "voted");
		
		return responseDto;
	}
	
}
