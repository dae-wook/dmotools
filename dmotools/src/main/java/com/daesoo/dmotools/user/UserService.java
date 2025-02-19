package com.daesoo.dmotools.user;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.daesoo.dmotools.common.auth.GoogleAuth;
import com.daesoo.dmotools.common.dto.ErrorMessage;
import com.daesoo.dmotools.common.entity.Character;
import com.daesoo.dmotools.common.entity.RefreshToken;
import com.daesoo.dmotools.common.entity.User;
import com.daesoo.dmotools.common.jwt.JwtUtil;
import com.daesoo.dmotools.common.repository.CharacterRepository;
import com.daesoo.dmotools.common.repository.RefreshTokenRepository;
import com.daesoo.dmotools.common.repository.UserRepository;
import com.daesoo.dmotools.user.dto.SignupRequestDto;
import com.daesoo.dmotools.user.dto.TokenDto;
import com.daesoo.dmotools.user.dto.UserResponseDto;

import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final CharacterRepository characterRepository;
	private final RefreshTokenRepository refreshTokenRepository;
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
		
		// Character 생성 로직
		List<Character> characterList = characterRepository.findAllByUser(user);
		if(characterList.isEmpty()) {
			characterRepository.save(Character.create("Character1", user));
		}
		
		// 토큰 생성
        String accessToken = jwtUtil.createAccessToken(user);
        String refreshToken = jwtUtil.createRefreshToken(user);
        
        // RefreshToken DB 저장
        saveRefreshToken(user.getEmail(), refreshToken);
		
		return UserResponseDto.of(user, accessToken, refreshToken);
		
	}
	
	@Transactional
    public TokenDto reissueAccessToken(String refreshToken) {
        // Refresh Token 검증
        try {
            jwtUtil.validateToken(refreshToken);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid Refresh Token");
        }

        // Refresh Token에서 사용자 정보 추출
        Claims claims = jwtUtil.getUserInfoFromToken(refreshToken);
        String userEmail = claims.getSubject();

        // DB에서 저장된 Refresh Token 조회
        RefreshToken savedToken = refreshTokenRepository.findById(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Refresh Token not found"));

        // 토큰 일치 여부 확인
        if (!savedToken.getRefreshToken().equals(refreshToken)) {
            throw new IllegalArgumentException("Refresh Token does not match");
        }

        // 새로운 Access Token 발급
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        String newAccessToken = jwtUtil.createAccessToken(user);

        return TokenDto.of(newAccessToken, refreshToken);
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
		
		String accessToken = jwtUtil.createAccessToken(user);
        String refreshToken = jwtUtil.createRefreshToken(user);
		
		characterRepository.save(Character.create("Character1", user));
		return UserResponseDto.of(user, accessToken, refreshToken);
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
		RefreshToken refreshToken = refreshTokenRepository.findById(user.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("Refresh Token not found"));
		String accessToken = jwtUtil.createAccessToken(user);
		
		return UserResponseDto.of(modifiedUser, accessToken, refreshToken.getRefreshToken());
	}
	
	private void saveRefreshToken(String userEmail, String refreshToken) {
        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .id(userEmail)
                .refreshToken(refreshToken)
                .expiryDate(LocalDateTime.now().plusDays(7))
                .build();
        refreshTokenRepository.save(refreshTokenEntity);
    }
	
}
