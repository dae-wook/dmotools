package com.daesoo.dmotools.alarm;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/alarms")
@RequiredArgsConstructor
//@CrossOrigin(origins = {"https://dmo-tools-dev.vercel.app"})
@Slf4j
public class AlarmController {
	
	private final AlarmService alarmService;
	
    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe() {
    	System.out.println("try subscribe");
        return alarmService.subscribe();
    }
    
    @GetMapping("/subscribers")
    public List<SseEmitter> getSubscribers() {
        return alarmService.getSubscribers();
    }
    
    @PostMapping("/notify")
    public void notify1() {
    	alarmService.notify("d", "d", "notify");
    }

}
