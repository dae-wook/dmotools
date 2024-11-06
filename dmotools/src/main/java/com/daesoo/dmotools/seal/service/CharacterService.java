package com.daesoo.dmotools.seal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.daesoo.dmotools.common.dto.ErrorMessage;
import com.daesoo.dmotools.common.entity.Character;
import com.daesoo.dmotools.common.entity.User;
import com.daesoo.dmotools.common.repository.CharacterRepository;
import com.daesoo.dmotools.common.repository.UserRepository;
import com.daesoo.dmotools.seal.dto.response.CharacterResponseDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CharacterService {
	
	private final CharacterRepository characterRepository;
	private final UserRepository userRepository;
	
	public List<CharacterResponseDto> getCharactersByLoginUser(User user) {
		
		return characterRepository.findAllByUser(user).stream().map(CharacterResponseDto::of).toList();
	}

	public CharacterResponseDto createCharacterByLoginMember(String name, User user) {
		
		if(characterRepository.findAllByUser(user).size() >= 5) {
			throw new IllegalArgumentException(ErrorMessage.CHARACTER_SLOT_IS_FULL.getMessage());
		}
		Character character = Character.create(name, user);
		characterRepository.save(character);
		return CharacterResponseDto.of(character);
	}

	@Transactional
	public CharacterResponseDto modifyCharacter(Long characterId, String name, User user) {
		
		Character character = characterRepository.findById(characterId).orElseThrow(
				() -> new IllegalArgumentException(ErrorMessage.CHARACTER_NOT_FOUND.getMessage())
				);
		
		if(!character.getUser().getId().equals(user.getId())) {
			throw new IllegalArgumentException(ErrorMessage.ACCESS_DENIED.getMessage());
		}
		
		character.update(name);
		
		return CharacterResponseDto.of(character);
	}
	
	@Transactional
	public CharacterResponseDto deleteCharacter(Long characterId, User user) {
		
		Character character = characterRepository.findById(characterId).orElseThrow(
				() -> new IllegalArgumentException(ErrorMessage.CHARACTER_NOT_FOUND.getMessage())
				);
		
		if(!character.getUser().getId().equals(user.getId())) {
			throw new IllegalArgumentException(ErrorMessage.ACCESS_DENIED.getMessage());
		}
		
		characterRepository.delete(character);
		
		return CharacterResponseDto.of(character);
	}
	
	
	
	
}
