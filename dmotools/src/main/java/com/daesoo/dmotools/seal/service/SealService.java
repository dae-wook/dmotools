package com.daesoo.dmotools.seal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.daesoo.dmotools.common.dto.ErrorMessage;
import com.daesoo.dmotools.common.dto.ServerType;
import com.daesoo.dmotools.common.dto.StatType;
import com.daesoo.dmotools.common.entity.Seal;
import com.daesoo.dmotools.common.entity.SealPrice;
import com.daesoo.dmotools.common.repository.SealPriceRepository;
import com.daesoo.dmotools.common.repository.SealRepository;
import com.daesoo.dmotools.seal.dto.SealPriceHistoryDto;
import com.daesoo.dmotools.seal.dto.response.SealPriceHistoryResponseDto;
import com.daesoo.dmotools.seal.dto.response.SealPriceResponseDto;
import com.daesoo.dmotools.seal.dto.response.SealResponseDto;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SealService {
	
	private final SealRepository sealRepository;
	private final SealPriceRepository sealPriceRepository;
	
	@Transactional
	public List<SealResponseDto> getAllSeals() {
		
		return sealRepository.findAllByOrderByStatTypeAscIdAsc().stream().map(SealResponseDto :: of).toList();
	}

	@Transactional
	public List<SealResponseDto> getSealsByStatType(StatType statType) {
		
		return sealRepository.findAllByStatType(statType).stream().map(SealResponseDto :: of).toList();
	}

	@Transactional
	public SealPriceResponseDto createPrice(Long sealId, int price) {
		
		Seal seal = sealRepository.findById(sealId).orElseThrow(
				() -> new EntityNotFoundException(ErrorMessage.SEAL_NOT_FOUND.getMessage())
				);
		
		if(!seal.isBuyable()) {
			throw new IllegalArgumentException(ErrorMessage.NO_PERMISSION.getMessage());
		}
		
		int minPrice = (int) (price * 0.95);
        int maxPrice = (int) (price * 1.05);
		
		List<SealPrice> sealPriceList = sealPriceRepository.findBySealAndPriceBetween(seal, minPrice, maxPrice);
		
		//등록된 근사값의 SealPrice가 없을 때 바로 등록
		if(sealPriceList.size() < 1) {
			SealPrice sealPrice = SealPrice.create(price, seal);
			sealPriceRepository.save(sealPrice);
			return SealPriceResponseDto.of(sealPrice);
		}
		//등록된 근사값의 SealPrice가 있을 때 중간 SealPrice의 regCount를 ++
		SealPrice sealPriceToUpdate = sealPriceList.get(sealPriceList.size() / 2);
        sealPriceToUpdate.incrementRegCount();
		
		return SealPriceResponseDto.of(sealPriceToUpdate);
	}

	@Transactional
	public List<SealPriceResponseDto> getOnePrice(String sortBy, ServerType serverType) {
		List<SealPrice> sealPriceList = new ArrayList<>();
		switch(sortBy) {
			case "regCount" : sealPriceList = sealPriceRepository.findLatestSealPricesOrderedByRegCount(); break;
			case "modifiedAt" : sealPriceList = sealPriceRepository.findLatestSealPricesByServerType(serverType.name()); break;
			default : throw new IllegalArgumentException(ErrorMessage.WRONG_SORT_PARAMETER.getMessage());
		}
				
		System.out.println(sealPriceList.size());
		
		return sealPriceList.stream().map(SealPriceResponseDto :: of).toList();
	}

	@Transactional
	public Page<SealPriceResponseDto> getSealPrice(Long sealId, Integer page, Integer size, String sortBy) {
		
		Seal seal = sealRepository.findById(sealId).orElseThrow(
				() -> new EntityNotFoundException(ErrorMessage.SEAL_NOT_FOUND.getMessage())
				);
		
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, sortBy));
		
		
		return sealPriceRepository.findAllBySeal(pageable, seal).map(SealPriceResponseDto::of);
	}
	
	@Transactional
	public List<SealPriceHistoryResponseDto> getSealPriceHistory() {
	    List<SealPrice> sealPriceList = sealPriceRepository.findAll();

	    Map<Long, List<SealPriceHistoryDto>> sealPriceHistoryMap = sealPriceList.parallelStream()
	        .collect(Collectors.groupingBy(
	            sealPrice -> sealPrice.getSeal().getId(),
	            Collectors.mapping(sealPrice -> SealPriceHistoryDto.create(sealPrice), // DTO로 변환
	                Collectors.toList())
	        ));

	    // Map을 List<SealPriceHistoryResponseDto>로 변환
	    return sealPriceHistoryMap.entrySet().stream()
	        .map(entry -> SealPriceHistoryResponseDto.of(entry.getKey(), entry.getValue()))
	        .collect(Collectors.toList());
	}

}
