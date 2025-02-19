package com.daesoo.dmotools.common.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "refresh_tokens")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

	@Id
	private String id; // user의 email을 id로 사용
	
    private String refreshToken;
    
    private LocalDateTime expiryDate;
	
}
