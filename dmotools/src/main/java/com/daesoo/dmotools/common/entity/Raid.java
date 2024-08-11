package com.daesoo.dmotools.common.entity;



import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "raids")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Raid {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String location;
    
	@OneToMany(mappedBy = "raid", cascade = CascadeType.ALL)
    private List<Timer> timers = new ArrayList<>();
}
