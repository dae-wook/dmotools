package com.daesoo.dmotools.gatcha.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daesoo.dmotools.common.dto.ResponseDto;
import com.daesoo.dmotools.gatcha.dto.GatchaResponseDto;
import com.daesoo.dmotools.gatcha.service.GatchaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gatcha")
@CrossOrigin(origins = {"http://localhost:3838", "https://dmo-tools.vercel.app", "https://dmo-tools-dev.vercel.app"})
public class GatchaController {

	private final GatchaService gatchaService;
	
	@GetMapping("/{type}")
	public ResponseDto<List<GatchaResponseDto>> getGatcha(@PathVariable("type") String type) {
		
		return ResponseDto.success(HttpStatus.OK, gatchaService.getGatcha(type));
		
	}
	
	@GetMapping
	public ResponseDto<List<GatchaResponseDto>> getAllGatcha() {
		
		return ResponseDto.success(HttpStatus.OK, gatchaService.getGatcha(null));
		
	}
	
}
