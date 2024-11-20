package com.daesoo.dmotools.raid;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.daesoo.dmotools.alarm.AlarmService;
import com.daesoo.dmotools.common.dto.ErrorMessage;
import com.daesoo.dmotools.common.dto.ServerType;
import com.daesoo.dmotools.common.entity.Client;
import com.daesoo.dmotools.common.entity.Raid;
import com.daesoo.dmotools.common.entity.Timer;
import com.daesoo.dmotools.common.entity.TimerVote;
import com.daesoo.dmotools.common.entity.User;
import com.daesoo.dmotools.common.repository.ClientRepository;
import com.daesoo.dmotools.common.repository.RaidRepository;
import com.daesoo.dmotools.common.repository.TimerRepository;
import com.daesoo.dmotools.common.repository.TimerVoteRepository;
import com.daesoo.dmotools.user.UserDetailsImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RaidService {

	private final RaidRepository raidRepository;
	private final TimerRepository timerRepository;
	private final ClientRepository clientRepository;
	private final TimerVoteRepository timerVoteRepository;
	private final AlarmService alarmService;
	
//	@Transactional
//	public List<TimerResponseDto> getTimers(ServerType server) {
//		return timerRepository.findAllByServerAndStartAtAfter(server, LocalDateTime.now()).stream().map(TimerResponseDto::of).toList();
//	}
	
	

//	public List<RaidResponseDto> getRaids() {
//		return raidRepository.findAll().stream().map(RaidResponseDto::of).toList();
//	}
	
	@Transactional
	public List<RaidResponseDto> getRaids(ServerType server) {
		
		List<Raid> raids = raidRepository.findAll();
		// 타이머 데이터를 미리 가져와서 그룹화
		Map<Long, List<TimerResponseDto>> timersByRaid = timerRepository.findAllByServerAndStartAtAfter(server, LocalDateTime.now())
				.stream()
				.collect(Collectors.groupingBy(timer -> timer.getRaid().getId(),
						Collectors.mapping(TimerResponseDto::of, Collectors.toList())));
		
		// 각 레이드에 타이머를 설정하여 DTO 변환
		return raids.stream()
				.map(raid -> {
					RaidResponseDto dto = RaidResponseDto.of(raid);
					dto.setTimer(timersByRaid.getOrDefault(raid.getId(), Collections.emptyList()));
					return dto;
				})
				.toList();
	}
	


	
	@Transactional
	public TimerResponseDto createTimer(Long raidId, TimerRequestDto dto, UserDetailsImpl userDetails) {

		Raid raid = raidRepository.findById(raidId).orElseThrow(
				() ->  new IllegalArgumentException(ErrorMessage.RAID_NOT_FOUND.getMessage()));
		
		
		Timer timer = Timer.create(dto, raid, userDetails);
		
//		timerRepository.save(timer)
		TimerResponseDto responseDto = TimerResponseDto.of(timerRepository.save(timer));
		alarmService.notify(responseDto, "time created", "created", dto.getServer());
		
		
		
		return responseDto;
	}

	@Transactional
	public TimerResponseDto voteTimer(Long timerId, Long clientId) {
		// TODO Auto-generated method stub
		
		Timer timer = timerRepository.findById(timerId).orElseThrow(
				() ->  new IllegalArgumentException(ErrorMessage.TIMER_NOT_FOUND.getMessage()));
		
		Client client = clientRepository.findById(clientId).orElseThrow(
				() ->  new IllegalArgumentException(ErrorMessage.CLIENT_NOT_FOUND.getMessage()));
		
	    if (timerVoteRepository.findByTimerAndClient(timer, client).isPresent()) {
	        throw new IllegalArgumentException(ErrorMessage.ALREADY_VOTED.getMessage());
	    }
		
		if(timer.getClientId() == clientId) {
			throw new IllegalArgumentException(ErrorMessage.ACCESS_DENIED.getMessage());
		}
		
		TimerVote timerVote = TimerVote.create(timer, client);
		timerVoteRepository.save(timerVote);
		
		timer.increaseVoteCount();
		
		TimerResponseDto responseDto = TimerResponseDto.of(timer);
		alarmService.notify(responseDto, "time voted", "voted", timer.getServer());
		
		return responseDto;
	}



	@Transactional
	public TimerResponseDto deleteTimer(Long timerId, User user) {

		Timer timer = timerRepository.findById(timerId).orElseThrow(
				() -> new IllegalArgumentException(ErrorMessage.TIMER_NOT_FOUND.getMessage())
				);
		
		if(!timer.getUser().getId().equals(user.getId())) {
			throw new IllegalArgumentException(ErrorMessage.ACCESS_DENIED.getMessage());
		}
		
		if(timer.isExpired()) {
			throw new IllegalArgumentException(ErrorMessage.ALREADY_EXPIRED_TIMER.getMessage());
		}
		
		alarmService.notify(TimerResponseDto.of(timer), "time removed", "removed", timer.getServer());
		timerRepository.delete(timer);
		
		return TimerResponseDto.of(timer);
	}

//	@Transactional
//	public List<TimerResponseDto> getTimers(ServerType server) {
//		// TODO Auto-generated method stub
//		timerRepository.findAllByServer(server);
//		
//		return timerRepository.findAllByServer(server).stream().map(TimerResponseDto::of).toList();
//	}
	
}
