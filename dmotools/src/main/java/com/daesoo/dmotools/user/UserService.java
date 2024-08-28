package com.daesoo.dmotools.user;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.daesoo.dmotools.common.auth.GoogleAuth;
import com.daesoo.dmotools.common.auth.GoogleIdTokenVerifier;
import com.daesoo.dmotools.common.dto.ErrorMessage;
import com.daesoo.dmotools.common.entity.User;
import com.daesoo.dmotools.common.jwt.JwtUtil;
import com.daesoo.dmotools.common.repository.UserRepository;
import com.daesoo.dmotools.user.dto.SignupRequestDto;
import com.daesoo.dmotools.user.dto.UserResponseDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final GoogleAuth googleAuth;
	private final JwtUtil jwtUtil;
	
	
	@Transactional
	public UserResponseDto login(String idToken) throws GeneralSecurityException, IOException {
		
		String email = googleAuth.getUserEmail(idToken);
		
		Optional<User> optionalUser = userRepository.findByEmail(email);
		
		if(!optionalUser.isPresent()) {
			return null;
		}
		
		User user = optionalUser.get();
		
		
		
		return UserResponseDto.of(user, jwtUtil.createToken(user));
		
	}

	@Transactional
	public UserResponseDto signup(SignupRequestDto signupRequestDto) throws GeneralSecurityException, IOException {
		
		String email = googleAuth.getUserEmail(signupRequestDto.getIdToken());
		if(userRepository.findByEmail(email).isPresent()) {
			throw new IllegalArgumentException(ErrorMessage.EMAIL_DUPLICATION.getMessage());
		}
		
		if(userRepository.findByNickname(signupRequestDto.getNickname()).isPresent()) {
			throw new IllegalArgumentException(ErrorMessage.USERNAME_DUPLICATION.getMessage());
		}
		
		
		User user = userRepository.save(User.create(email, signupRequestDto.getNickname()));
		return UserResponseDto.of(user, jwtUtil.createToken(user));
	}

	public Boolean getUserByNickname(String nickname) {

		Optional<User> optionalUser = userRepository.findByNickname(nickname);
		
		if(optionalUser.isPresent()) {
			return true;
		}
		
		return false;
	}

	@Transactional
	public Boolean resign(User user) {

		userRepository.delete(user);
		
		return true;
	}

	@Transactional
	public UserResponseDto modifyUser(User user, String nickname) {
		// TODO Auto-generated method stub
		System.out.println(nickname);
		user.modify(nickname);
		
		User modifiedUser = userRepository.save(user);
		
		
		
		return UserResponseDto.of(modifiedUser, jwtUtil.createToken(modifiedUser));
	}
	
}
