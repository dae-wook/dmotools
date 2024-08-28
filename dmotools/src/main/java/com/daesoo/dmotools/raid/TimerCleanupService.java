package com.daesoo.dmotools.raid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.daesoo.dmotools.alarm.AlarmService;
import com.daesoo.dmotools.common.entity.Timer;
import com.daesoo.dmotools.common.entity.User;
import com.daesoo.dmotools.common.repository.TimerRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TimerCleanupService {

    private final TimerRepository timerRepository;
    private final AlarmService alarmService;
    

    @Scheduled(fixedRate = 2000) // 1분마다 실행
    @Transactional
    public void deleteExpiredTimers() {
        LocalDateTime now = LocalDateTime.now();
        List<Timer> expiredTimers = timerRepository.findAllByStartAtBefore(now);
        for(Timer timer : expiredTimers) {
        	if(timer.getUser() != null) {
        		timer.getUser().increaseCompleteCount();
        		
        	}
        }
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
