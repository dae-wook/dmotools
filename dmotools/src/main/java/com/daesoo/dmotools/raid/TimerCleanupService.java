package com.daesoo.dmotools.raid;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.daesoo.dmotools.alarm.AlarmService;
import com.daesoo.dmotools.common.entity.Timer;
import com.daesoo.dmotools.common.repository.TimerRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TimerCleanupService {

    private final TimerRepository timerRepository;
    private final AlarmService alarmService;
    

    @Scheduled(fixedRate = 60000) // 1분마다 실행
    @Transactional
    public void deleteExpiredTimers() {
        LocalDateTime now = LocalDateTime.now();
        List<Timer> expiredTimers = timerRepository.findAllByStartAtBefore(now);
        List<Timer> sameRaidTimers = new ArrayList();
        for(Timer timer : expiredTimers) {
        	List<Timer> innerTimers = timerRepository.findAllByRaidAndChannel(timer.getRaid(), timer.getChannel());
        	if(innerTimers.size() > 1) {
        		for(Timer sameTimer : innerTimers) {
        			sameRaidTimers.add(sameTimer);
        			alarmService.notify(TimerResponseDto.of(sameTimer), "time deleted", "removed", sameTimer.getServer());
        		}
        	}
        	timerRepository.deleteAll(timerRepository.findAllByRaid(timer.getRaid()));
        	
        	if(timer.getUser() != null) {
        		timer.getUser().increaseCompleteCount();
        		
        	}
        }
        timerRepository.deleteAll(sameRaidTimers);
        timerRepository.deleteAll(expiredTimers);
//        for(Timer timer : expiredTimers) {
//        	
//        	alarmService.notify(TimerResponseDto.of(timer), "time deleted", "removed", timer.getServer());
//        }
    }
    
    @Scheduled(fixedRate = 30000) // 1분마다 실행
    @Transactional
    public void sendPing() {
        LocalDateTime now = LocalDateTime.now();
        List<Timer> expiredTimers = timerRepository.findAllByStartAtBefore(now);
        timerRepository.deleteAll(expiredTimers);
//        	
        alarmService.notify("pong", "ping", "ping", null);
    }
}
