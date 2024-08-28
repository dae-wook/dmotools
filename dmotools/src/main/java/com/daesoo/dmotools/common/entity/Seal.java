package com.daesoo.dmotools.common.entity;



import java.util.ArrayList;
import java.util.List;

import com.daesoo.dmotools.common.dto.StatType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "seals")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Seal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String engName;
	
	private Integer maxIncrease;
	
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatType statType;
    
    private boolean buyable = true;
    
    private int masterCount;

    @OneToMany(mappedBy = "seal", cascade = CascadeType.ALL)
    private List<SealPrice> sealPriceList = new ArrayList<>();
}
