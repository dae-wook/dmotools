package com.daesoo.dmotools.seal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.daesoo.dmotools.common.dto.ErrorMessage;
import com.daesoo.dmotools.common.entity.Seal;
import com.daesoo.dmotools.common.entity.User;
import com.daesoo.dmotools.common.entity.UserPrice;
import com.daesoo.dmotools.common.entity.UserSeal;
import com.daesoo.dmotools.common.repository.SealRepository;
import com.daesoo.dmotools.common.repository.UserPriceRepository;
import com.daesoo.dmotools.common.repository.UserSealRepository;
import com.daesoo.dmotools.seal.dto.request.InventoryRequestDto;
import com.daesoo.dmotools.seal.dto.response.UserPriceResponseDto;
import com.daesoo.dmotools.seal.dto.response.UserSealResponseDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {
	
	private final UserSealRepository inventoryRepository;
	private final SealRepository sealRepository;
	private final UserPriceRepository userPriceRepository;
	
	public List<UserSealResponseDto> getInventoryByLoginUser(User user) {
		
		return inventoryRepository.findAllByUser(user).stream().map(UserSealResponseDto::of).toList();
	}
	
	public List<UserPriceResponseDto> getUserPriceByLoginUser(User user) {
		
		return userPriceRepository.findAllByUser(user).stream().map(UserPriceResponseDto::of).toList();
	}

	@Transactional
	public UserSealResponseDto updateSealCount(User user, Long sealId, InventoryRequestDto dto) {
		// TODO Auto-generated method stub
		Seal seal = sealRepository.findById(sealId).orElseThrow(
				() -> new IllegalArgumentException(ErrorMessage.SEAL_NOT_FOUND.getMessage()));
		
		
		UserSeal userSeal = inventoryRepository.findByUserAndSeal(user, seal)
			    .orElseGet(() -> inventoryRepository.save(UserSeal.create(dto, user, seal)));
		
		if(dto.getCount() == 0) {
			inventoryRepository.delete(userSeal);
			return null;
		}
		
		userSeal.update(dto);
		
		return UserSealResponseDto.of(userSeal);
	}
	
	@Transactional
	public UserPriceResponseDto updateSealPrice(User user, Long sealId, InventoryRequestDto dto) {
		// TODO Auto-generated method stub
		Seal seal = sealRepository.findById(sealId).orElseThrow(
				() -> new IllegalArgumentException(ErrorMessage.SEAL_NOT_FOUND.getMessage()));
		
		
		UserPrice userPrice = userPriceRepository.findByUserAndSeal(user, seal)
			    .orElseGet(() -> userPriceRepository.save(UserPrice.create(dto, user, seal)));
		
		if(dto.getPrice() == 0) {
			userPriceRepository.delete(userPrice);
			return null;
		}
		
		userPrice.update(dto);
		
		return UserPriceResponseDto.of(userPrice);
	}
	
	
}
