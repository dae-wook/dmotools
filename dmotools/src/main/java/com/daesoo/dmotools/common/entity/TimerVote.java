package com.daesoo.dmotools.common.entity;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "timer_votes")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TimerVote {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Timer timer;
	
	@ManyToOne
	private Client client;
	
	public static TimerVote create(Timer timer, Client client) {
		return TimerVote.builder()
				.timer(timer)
				.client(client)
				.build();
	}
	
}
