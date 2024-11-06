package com.daesoo.dmotools.seal.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.daesoo.dmotools.common.dto.ErrorMessage;
import com.daesoo.dmotools.common.entity.Character;
import com.daesoo.dmotools.common.entity.Seal;
import com.daesoo.dmotools.common.entity.User;
import com.daesoo.dmotools.common.entity.UserPrice;
import com.daesoo.dmotools.common.entity.UserSeal;
import com.daesoo.dmotools.common.repository.CharacterRepository;
import com.daesoo.dmotools.common.repository.SealRepository;
import com.daesoo.dmotools.common.repository.UserPriceRepository;
import com.daesoo.dmotools.common.repository.UserRepository;
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
	private final UserRepository userRepository;
	private final CharacterRepository characterRepository;
	
	public List<UserSealResponseDto> getInventoryByLoginUser(User user) {
		
		return inventoryRepository.findAllByUser(user).stream().map(UserSealResponseDto::of).toList();
	}
	
	public List<UserSealResponseDto> getInventoryByLoginUser(Long characterId, User user) {
		Character character = characterRepository.findById(characterId).orElseThrow(
				() -> new IllegalArgumentException(ErrorMessage.SEAL_NOT_FOUND.getMessage()));
		System.out.println(inventoryRepository.findAllByCharacter(character).stream().map(UserSealResponseDto::of).toList().size());
		
		return inventoryRepository.findAllByCharacter(character).stream().map(UserSealResponseDto::of).toList();
	}
	
	@Transactional
	public void renew() {
		//1.유저 수 만큼 Character 생성
		List<UserSeal> userSealList = inventoryRepository.findAllByCharacterIsNull();
		System.out.println(userSealList.size());
		for(UserSeal userSeal : userSealList) {
			Character newCharacter = null;
			Optional<Character> optionalCharacter = characterRepository.findByUser(userSeal.getUser());
			if(!optionalCharacter.isPresent()) {
				Character character = Character.create("Character1", userSeal.getUser());
				newCharacter = characterRepository.save(character);
				userSeal.update(newCharacter);
				continue;
			}
			userSeal.update(optionalCharacter.get());
			
		}
		
		
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
			    .orElseGet(() -> inventoryRepository.save(UserSeal.create(dto, user, seal, null)));
		
		if(dto.getCount() == 0) {
			inventoryRepository.delete(userSeal);
			return null;
		}
		
		userSeal.update(dto);
		
		return UserSealResponseDto.of(userSeal);
	}
	
	@Transactional
	public UserSealResponseDto updateSealCountByCharacter(User user, Long sealId, Long characterId, InventoryRequestDto dto) {
		// TODO Auto-generated method stub
		Seal seal = sealRepository.findById(sealId).orElseThrow(
				() -> new IllegalArgumentException(ErrorMessage.SEAL_NOT_FOUND.getMessage()));
		
		
		Character character = characterRepository.findById(characterId).orElseThrow(
				() -> new IllegalArgumentException(ErrorMessage.CHARACTER_NOT_FOUND.getMessage())
				);
		
		UserSeal userSeal = inventoryRepository.findByUserAndSealAndCharacter(user, seal, character)
			    .orElseGet(() -> inventoryRepository.save(UserSeal.create(dto, user, seal, character)));
		
		if(character.getUser().getId() != userSeal.getUser().getId()) {
			throw new IllegalArgumentException(ErrorMessage.ACCESS_DENIED.getMessage());
		}
		
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
