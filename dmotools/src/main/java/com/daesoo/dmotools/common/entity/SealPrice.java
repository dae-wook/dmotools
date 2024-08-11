package com.daesoo.dmotools.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "seal_price")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SealPrice extends TimeStamp{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private double price;
	
	private int regCount;
	
	@ManyToOne
	private Seal seal;
	
	public static SealPrice create(int price, Seal seal) {
		return SealPrice.builder()
				.price(price)
				.seal(seal)
				.build();
	}
	
	public void incrementRegCount() {
        this.regCount++;
    }
	
}
