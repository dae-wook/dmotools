package com.daesoo.dmotools.common.entity;



import com.daesoo.dmotools.seal.dto.request.InventoryRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "user_seals")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSeal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    private Integer count;
	
	@ManyToOne
	private Seal seal;
	
	@ManyToOne
	private User user;
	
	public static UserSeal create(InventoryRequestDto dto, User user, Seal seal) {
		return UserSeal.builder()
			.count(dto.getCount())
			.seal(seal)
			.user(user)
			.build();
	}

	public void update(InventoryRequestDto dto) {
		// TODO Auto-generated method stub
		this.count = dto.getCount();
		
	}
}
