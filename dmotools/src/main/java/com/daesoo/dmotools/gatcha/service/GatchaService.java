package com.daesoo.dmotools.gatcha.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.daesoo.dmotools.common.repository.GatchaRepository;
import com.daesoo.dmotools.gatcha.dto.GatchaResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GatchaService {
	
	private final GatchaRepository gatchaRepository;
	
	public List<GatchaResponseDto> getGatcha() {
		
		return gatchaRepository.findAllByVisibleTrue().stream().map(GatchaResponseDto::of).toList();
	}

}
