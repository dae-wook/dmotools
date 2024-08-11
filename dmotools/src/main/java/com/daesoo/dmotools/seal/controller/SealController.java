package com.daesoo.dmotools.seal.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daesoo.dmotools.common.dto.ResponseDto;
import com.daesoo.dmotools.common.dto.StatType;
import com.daesoo.dmotools.seal.SealPriceResponseDto;
import com.daesoo.dmotools.seal.SealResponseDto;
import com.daesoo.dmotools.seal.service.SealService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/seals")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3838", "https://dmo-tools.vercel.app", "https://dmo-tools-dev.vercel.app"})
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
	
	@PostMapping("/{sealId}")
	public ResponseDto<SealPriceResponseDto> createPrice(
			@PathVariable("sealId") Long sealId,
			@RequestParam("price") int price) {
		
		return ResponseDto.success(HttpStatus.CREATED, sealService.createPrice(sealId, price));
	}
	
	@GetMapping("/price")
	public ResponseDto<List<SealPriceResponseDto>> getOnePrice(
			@RequestParam(name = "sortBy", defaultValue = "regCount") String sortBy) {
		
		return ResponseDto.success(HttpStatus.OK, sealService.getOnePrice(sortBy));
	}
	
	@GetMapping("/{sealId}/price")
	public ResponseDto<Page<SealPriceResponseDto>> getSealPrice(
			@PathVariable("sealId") Long sealId,
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "size", defaultValue = "5") Integer size,
			@RequestParam(name = "sortBy", defaultValue = "regCount") String sortBy) {
		
		return ResponseDto.success(HttpStatus.OK, sealService.getSealPrice(sealId, page, size, sortBy));
	}
}
