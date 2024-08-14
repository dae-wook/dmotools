package com.daesoo.dmotools.alarm;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.daesoo.dmotools.common.dto.ServerType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Emitter {

	private Long clientId;
	private SseEmitter emitter;
	private ServerType server;
	
	public static Emitter create(Long id, SseEmitter emitter, ServerType server ) {
		return Emitter.builder()
				.clientId(id)
				.emitter(emitter)
				.server(server)
				.build();
	}
	
	public void changeServer(ServerType server) {
		this.server = server;
	}
	
}
