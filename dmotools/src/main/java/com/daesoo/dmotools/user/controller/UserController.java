package com.daesoo.dmotools.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daesoo.dmotools.common.dto.ErrorMessage;
import com.daesoo.dmotools.common.dto.ResponseDto;
import com.daesoo.dmotools.user.UserDetailsImpl;
import com.daesoo.dmotools.user.UserService;
import com.daesoo.dmotools.user.dto.SignupRequestDto;
import com.daesoo.dmotools.user.dto.UserResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@CrossOrigin(origins = {"http://localhost:3838", "https://dmo-tools.vercel.app", "https://dmo-tools-dev.vercel.app", "https://dmo.greuta.org", "https://www.greuta.org"})
public class UserController {

	private final UserService userService;
	
	@PostMapping("/login")
	public ResponseDto<UserResponseDto> login(@RequestParam("idToken") String idToken) throws Exception {
		return ResponseDto.success(HttpStatus.OK, userService.login(idToken));
	}
	
	@PostMapping("/signup")
	public ResponseDto<UserResponseDto> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) throws Exception {
		System.out.println(signupRequestDto.getIdToken());
		System.out.println(signupRequestDto.getNickname());
		return ResponseDto.success(HttpStatus.OK, userService.signup(signupRequestDto)); 
	}
	
	@GetMapping("/{nickname}/check")
	public ResponseDto<Boolean> existUserByNickname(@PathVariable("nickname") String nickname) {
		return ResponseDto.success(HttpStatus.OK, userService.getUserByNickname(nickname));
	}
	
	@PutMapping("/modify")
	public ResponseDto<UserResponseDto> modifyUser(
			@AuthenticationPrincipal UserDetailsImpl userDetails,
			@RequestParam("nickname") String nickname) {
		
		if (userDetails == null) {
	        throw new IllegalArgumentException(ErrorMessage.UNAHTHORIZED.getMessage());
	    }
		
		return ResponseDto.success(HttpStatus.OK, userService.modifyUser(userDetails.getUser(),nickname));
	}
	
	@PostMapping("/resign")
	public ResponseDto<Boolean> resign(@AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
		
		if (userDetails == null) {
	        throw new IllegalArgumentException(ErrorMessage.UNAHTHORIZED.getMessage());
	    }
		
		return ResponseDto.success(HttpStatus.OK, userService.resign(userDetails.getUser())); 
	}
	
}
