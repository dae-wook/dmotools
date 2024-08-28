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

@Entity(name = "user_prices")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPrice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    private Integer customPrice;
	
	@ManyToOne
	private Seal seal;
	
	@ManyToOne
	private User user;
	
	public static UserPrice create(InventoryRequestDto dto, User user, Seal seal) {
		return UserPrice.builder()
			.customPrice(dto.getPrice())
			.seal(seal)
			.user(user)
			.build();
	}

	public void update(InventoryRequestDto dto) {
		// TODO Auto-generated method stub
		this.customPrice = dto.getPrice();
		
	}
}
