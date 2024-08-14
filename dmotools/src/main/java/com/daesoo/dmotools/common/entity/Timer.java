package com.daesoo.dmotools.common.entity;



import java.time.LocalDateTime;

import com.daesoo.dmotools.common.dto.ServerType;
import com.daesoo.dmotools.raid.TimerRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "timers")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Timer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Long clientId;
	
	@Column(nullable = false)
	private LocalDateTime startAt;
	
	@Column(nullable = false)
	private int channel;
	
	private int voteCount;
	
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
	private ServerType server;
    
	@ManyToOne
	public Raid raid;
	
	public static Timer create(TimerRequestDto dto, Raid raid) {
		return Timer.builder()
				.clientId(dto.getClientId())
				.startAt(dto.getStartAt())
				.channel(dto.getChannel())
				.voteCount(dto.getVoteCount())
				.server(dto.getServer())
				.raid(raid)
				.build();
	}
	
	public void increaseVoteCount() {
		this.voteCount++;
	}
	
	
}
