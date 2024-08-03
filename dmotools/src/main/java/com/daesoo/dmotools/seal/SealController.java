package com.daesoo.dmotools.seal;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daesoo.dmotools.common.dto.ResponseDto;
import com.daesoo.dmotools.common.dto.StatType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/seals")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3737", "https://terracotta-nu.vercel.app"})
@Slf4j
public class SealController {
	
	
	private final SealService sealService;
	
	@GetMapping
	public ResponseDto<List<SealResponseDto>> getAllSeals() {
		
		return ResponseDto.success(HttpStatus.OK, sealService.getAllSeals());
	}
	
	@GetMapping("/{statType}")
	public ResponseDto<List<SealResponseDto>> getSealsByStatType(
            @PathVariable(name="statType") StatType statType) {
		
		return ResponseDto.success(HttpStatus.OK, sealService.getSealsByStatType(statType));
	}
	
	



}
