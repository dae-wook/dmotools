package com.daesoo.dmotools.seal.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daesoo.dmotools.common.dto.ErrorMessage;
import com.daesoo.dmotools.common.dto.ResponseDto;
import com.daesoo.dmotools.seal.dto.response.CharacterResponseDto;
import com.daesoo.dmotools.seal.service.CharacterService;
import com.daesoo.dmotools.user.UserDetailsImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3838", "https://dmo-tools.vercel.app", "https://dmo-tools-dev.vercel.app", "https://dmo.greuta.org", "https://www.greuta.org"})
@Slf4j
@RequestMapping("/api/characters")
public class CharacterController {
	
	private final CharacterService characterService;
	
	@GetMapping
	public ResponseDto<List<CharacterResponseDto>> getCharactersByLoginUser(
			@AuthenticationPrincipal UserDetailsImpl userDetails
			) {
		
		if (userDetails == null) {
	        throw new IllegalArgumentException(ErrorMessage.UNAHTHORIZED.getMessage());
	    }
		
		return ResponseDto.success(HttpStatus.OK, characterService.getCharactersByLoginUser(userDetails.getUser()));
	}
	
	@PostMapping
	public ResponseDto<CharacterResponseDto> createCharacterByLoginMember(
			@RequestParam(name = "name") String name,
			@AuthenticationPrincipal UserDetailsImpl userDetails
			) {
		
		if (userDetails == null) {
	        throw new IllegalArgumentException(ErrorMessage.UNAHTHORIZED.getMessage());
	    }
		
		return ResponseDto.success(HttpStatus.OK, characterService.createCharacterByLoginMember(name, userDetails.getUser()));
	}
	
	@PutMapping("/{characterId}")
	public ResponseDto<CharacterResponseDto> modifyCharacter(
			@PathVariable("characterId")Long characterId,
			@RequestParam(name = "name") String name,
			@AuthenticationPrincipal UserDetailsImpl userDetails){
		
		if (userDetails == null) {
	        throw new IllegalArgumentException(ErrorMessage.UNAHTHORIZED.getMessage());
	    }
		
		return ResponseDto.success(HttpStatus.OK, characterService.modifyCharacter(characterId, name, userDetails.getUser()));
	}
	
	@DeleteMapping("/{characterId}")
	public ResponseDto<CharacterResponseDto> deleteCharacter(
			@PathVariable("characterId")Long characterId,
			@AuthenticationPrincipal UserDetailsImpl userDetails){
		
		if (userDetails == null) {
	        throw new IllegalArgumentException(ErrorMessage.UNAHTHORIZED.getMessage());
	    }
		
		return ResponseDto.success(HttpStatus.OK, characterService.deleteCharacter(characterId, userDetails.getUser()));
	}
}
