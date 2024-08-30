package com.daesoo.dmotools.common.entity;



import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "gatcha")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Gatcha {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	private Boolean visible;
	
	private int ticketCost;
	
	private int maxDrawCount;
	
	private String type;
	
	@OneToMany(mappedBy = "gatcha")
    private List<Item> items = new ArrayList<>();
	
	@OneToMany(mappedBy = "gatcha", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GachaItem> gatchaItems = new ArrayList<>();
}
