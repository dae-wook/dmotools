package com.daesoo.dmotools.alarm;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.daesoo.dmotools.common.dto.ServerType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/alarms")
@RequiredArgsConstructor
//@CrossOrigin(origins = {"https://dmo-tools-dev.vercel.app"})
@Slf4j
public class AlarmController {
	
	private final AlarmService alarmService;
	
    @GetMapping(value = "/subscribe/{server}", produces = "text/event-stream")
    public SseEmitter subscribe(@PathVariable("server") ServerType serverType) {
        return alarmService.subscribe(serverType);
    }
    
    @PostMapping(value = "/{clientId}/dis-subscribe")
    public boolean cancel(@PathVariable("clientId") Long clientId) {
        return alarmService.cancel(clientId);
    }
    
    @PutMapping(value = "/{clientId}")
    public boolean changeServer(@PathVariable("clientId") Long clientId,
    		@RequestParam("server") ServerType server) {
        return alarmService.change(clientId, server);
    }
    
    @GetMapping("/{server}/subscribers")
    public Map<Long, Emitter> getSubscribers(@PathVariable("server") ServerType serverType) {
        return alarmService.getSubscribers();
    }
    
    @PostMapping("/{server}/notify")
    public void notify1(@PathVariable("server") ServerType serverType) {
    	alarmService.notify("d", "d", "notify", serverType);
    }

}
