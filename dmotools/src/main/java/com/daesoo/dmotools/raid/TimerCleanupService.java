package com.daesoo.dmotools.raid;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.daesoo.dmotools.alarm.AlarmService;
import com.daesoo.dmotools.common.entity.Timer;
import com.daesoo.dmotools.common.repository.TimerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TimerCleanupService {

    private final TimerRepository timerRepository;
    private final AlarmService alarmService;
    

    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void deleteExpiredTimers() {
        LocalDateTime now = LocalDateTime.now();
        List<Timer> expiredTimers = timerRepository.findAllByStartAtBefore(now);
        timerRepository.deleteAll(expiredTimers);
        for(Timer timer : expiredTimers) {
        	
        	alarmService.notify(TimerResponseDto.of(timer), "time deleted", "removed");
        }
    }
}
