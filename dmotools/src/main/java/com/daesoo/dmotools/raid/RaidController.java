package com.daesoo.dmotools.raid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daesoo.dmotools.common.dto.ResponseDto;
import com.daesoo.dmotools.common.dto.ServerType;
import com.daesoo.dmotools.user.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/raids")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3838", "https://dmo-tools.vercel.app", "https://dmo-tools-dev.vercel.app", "https://dmo.greuta.org", "https://www.greuta.org"})
public class RaidController {

	private final RaidService raidService;
	
	@GetMapping("/{server}")
	public ResponseDto<List<RaidResponseDto>> getRaids(@PathVariable("server") ServerType server) {
		return ResponseDto.success(HttpStatus.OK, raidService.getRaids(server));
	}
	
//	@GetMapping("/{server}/timers")
//	public ResponseDto<List<TimerResponseDto>> getTimers(@PathVariable("server") ServerType server) {
//		return ResponseDto.success(HttpStatus.OK, raidService.getTimers(server));
//	}
	
	
	@PostMapping("/{raidId}/timers")
	public ResponseDto<TimerResponseDto> createTimer(
			@PathVariable("raidId") Long raidId,
			@RequestBody TimerRequestDto dto,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		
		return ResponseDto.success(HttpStatus.CREATED, raidService.createTimer(raidId, dto, userDetails));
	}
	
	@PutMapping("/timers/{timerId}/vote")
	public ResponseDto<TimerResponseDto> voteTimer(
			@PathVariable("timerId") Long timerId,
			@RequestParam("clientId") Long clientId) {
		
		return ResponseDto.success(HttpStatus.CREATED, raidService.voteTimer(timerId, clientId));
	}
	
}
