package com.daesoo.dmotools.raid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daesoo.dmotools.common.dto.ResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/raids")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3838", "https://dmo-tools.vercel.app", "https://dmo-tools-dev.vercel.app"})
public class RaidController {

	private final RaidService raidService;
	
	@GetMapping
	public ResponseDto<List<RaidResponseDto>> getRaids() {
		return ResponseDto.success(HttpStatus.OK, raidService.getRaids());
	}
	
	@PostMapping("/{raidId}/timers")
	public ResponseDto<TimerResponseDto> createTimer(
			@PathVariable("raidId") Long raidId,
			@RequestBody TimerRequestDto dto) {
		
		return ResponseDto.success(HttpStatus.CREATED, raidService.createTimer(raidId, dto));
	}
	
	@PutMapping("/timers/{timerId}/vote")
	public ResponseDto<TimerResponseDto> voteTimer(
			@PathVariable("timerId") Long timerId) {
		
		return ResponseDto.success(HttpStatus.CREATED, raidService.voteTimer(timerId));
	}
	
}
