package com.daesoo.dmotools.seal.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daesoo.dmotools.common.dto.ErrorMessage;
import com.daesoo.dmotools.common.dto.ResponseDto;
import com.daesoo.dmotools.seal.dto.request.InventoryRequestDto;
import com.daesoo.dmotools.seal.dto.response.UserPriceResponseDto;
import com.daesoo.dmotools.seal.dto.response.UserSealResponseDto;
import com.daesoo.dmotools.seal.service.InventoryService;
import com.daesoo.dmotools.user.UserDetailsImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3838", "https://dmo-tools.vercel.app", "https://dev-dmo.greuta.org", "https://dmo.greuta.org", "https://www.greuta.org"})
@Slf4j
@RequestMapping("/api/seals/my")
public class InventoryController {
	
	private final InventoryService inventoryService;
	
	@GetMapping("/renew")
	public ResponseDto<String> getUserSealsByLoginUser() {
		inventoryService.renew();
		
		return ResponseDto.success(HttpStatus.OK, "성공");
	}
	
	@GetMapping("/count")
	public ResponseDto<List<UserSealResponseDto>> getUserSealsByLoginUser(
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		
		if (userDetails == null) {
	        throw new IllegalArgumentException(ErrorMessage.UNAHTHORIZED.getMessage());
	    }
		
		return ResponseDto.success(HttpStatus.OK, inventoryService.getInventoryByLoginUser(userDetails.getUser()));
	}
	
	@GetMapping("/{characterId}/count")
	public ResponseDto<List<UserSealResponseDto>> getUserSealsByLoginUser(
			@PathVariable("characterId")Long characterId) {
		
//		if (userDetails == null) {
//	        throw new IllegalArgumentException(ErrorMessage.UNAHTHORIZED.getMessage());
//	    }
		
		return ResponseDto.success(HttpStatus.OK, inventoryService.getInventoryByLoginUser(characterId, null));
	}
	
	
	@GetMapping("/price")
	public ResponseDto<List<UserPriceResponseDto>> getUserPriceByLoginUser(
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		
		if (userDetails == null) {
	        throw new IllegalArgumentException(ErrorMessage.UNAHTHORIZED.getMessage());
	    }
		
		return ResponseDto.success(HttpStatus.OK, inventoryService.getUserPriceByLoginUser(userDetails.getUser()));
	}
	
	@PutMapping("/{sealId}/count")
	public ResponseDto<UserSealResponseDto> updateSealCount(
			@AuthenticationPrincipal UserDetailsImpl userDetails,
			@PathVariable("sealId") Long sealId,
			@RequestBody InventoryRequestDto dto) {	
		
		if (userDetails == null) {
	        throw new IllegalArgumentException(ErrorMessage.UNAHTHORIZED.getMessage());
	    }
		
		
		return ResponseDto.success(HttpStatus.OK, inventoryService.updateSealCount(userDetails.getUser(), sealId, dto));
	}
	
	@PutMapping("/{characterId}/{sealId}/count")
	public ResponseDto<UserSealResponseDto> updateSealCountByCharacter(
			@AuthenticationPrincipal UserDetailsImpl userDetails,
			@PathVariable("sealId") Long sealId,
			@PathVariable("characterId") Long characterId,
			@RequestBody InventoryRequestDto dto) {	
		
		if (userDetails == null) {
	        throw new IllegalArgumentException(ErrorMessage.UNAHTHORIZED.getMessage());
	    }
		
		
		return ResponseDto.success(HttpStatus.OK, inventoryService.updateSealCountByCharacter(userDetails.getUser(), sealId, characterId, dto));
	}
	
	@PutMapping("/{sealId}/price")
	public ResponseDto<UserPriceResponseDto> updateSealPrice(
			@AuthenticationPrincipal UserDetailsImpl userDetails,
			@PathVariable("sealId") Long sealId,
			@RequestBody InventoryRequestDto dto) {	
		
		if (userDetails == null) {
	        throw new IllegalArgumentException(ErrorMessage.UNAHTHORIZED.getMessage());
	    }
		
		
		return ResponseDto.success(HttpStatus.OK, inventoryService.updateSealPrice(userDetails.getUser(), sealId, dto));
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
