package com.daesoo.dmotools.common.entity;



import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "characters")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Character {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy = "character", cascade = CascadeType.ALL)
	private List<UserSeal> userSealList = new ArrayList<>();
	
	public static Character create(String name, User user) {
		return Character.builder()
				.name(name)
				.user(user)
				.build();
	}
	
	public void update(String name) {
		this.name = name;
	}
}
