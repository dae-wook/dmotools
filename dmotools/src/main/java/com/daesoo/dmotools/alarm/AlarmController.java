package com.daesoo.dmotools.alarm;

import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.daesoo.dmotools.common.dto.ResponseDto;
import com.daesoo.dmotools.common.dto.ServerType;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/alarms")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3838"})
@Slf4j
public class AlarmController {
	
	private final AlarmService alarmService;
	
    private static final Pattern IPV4_PATTERN = Pattern.compile(
            "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"
        );

	
    @GetMapping(value = "/subscribe/{server}", produces = "text/event-stream")
    public SseEmitter subscribe(@PathVariable("server") ServerType serverType,
    		@RequestParam("clientId") Long clientId) {
        return alarmService.subscribe(serverType, clientId);
    }
    
    @GetMapping("/client") ResponseDto<Long> getClientId(@RequestParam("ipAddress") String ipAddress) {
    	return ResponseDto.success(HttpStatus.OK, alarmService.getClientId(ipAddress));
    }
    
    @GetMapping("/client-id")
    public ResponseDto<Long> getClientId(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("X-Real-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        // 여러 개의 IP가 있을 수 있으므로 첫 번째 IP만 추출
        if (ipAddress != null && ipAddress.contains(",")) {
            ipAddress = ipAddress.split(",")[0].trim();
        }

        // IPv4 형식인지 확인
        if (ipAddress != null && IPV4_PATTERN.matcher(ipAddress).matches()) {
        	return ResponseDto.success(HttpStatus.OK, alarmService.getClientId(ipAddress));
        } else {
            return null;
        }
    }
    

    @GetMapping("/client-ip")
    public String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("X-Real-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return "Client IP: " + ipAddress;
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
