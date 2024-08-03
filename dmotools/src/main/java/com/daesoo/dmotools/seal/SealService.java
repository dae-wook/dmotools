package com.daesoo.dmotools.seal;

import java.util.List;

import org.springframework.stereotype.Service;

import com.daesoo.dmotools.common.dto.StatType;
import com.daesoo.dmotools.common.repository.SealRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SealService {
	
	private final SealRepository sealRepository;
	
	public List<SealResponseDto> getAllSeals() {
		
		return sealRepository.findAll().stream().map(SealResponseDto :: of).toList();
	}

	
	public List<SealResponseDto> getSealsByStatType(StatType statType) {
		
		return sealRepository.findAllByStatType(statType).stream().map(SealResponseDto :: of).toList();
	}

}
