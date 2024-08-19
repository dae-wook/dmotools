package com.daesoo.dmotools.alarm;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.daesoo.dmotools.common.dto.ServerType;
import com.daesoo.dmotools.common.entity.Client;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmitterRepository {

	private final Map<Long, Emitter> emitters = new ConcurrentHashMap<>();

    public Long save(SseEmitter emitter, Long clientId, ServerType serverType) {
    	emitters.put(clientId, Emitter.create(clientId, emitter, serverType));
    	return clientId;
    }
    
    public void update(Emitter emitter) {
    	emitters.put(emitter.getClientId(), emitter);
    }

    public void delete(Long clientId) {
    		
    	emitters.remove(clientId);
    }
    
    public Map<Long, Emitter> findAll() {
    	return emitters;
    }
    
    public Map<Long, Emitter> findAllByServerType(ServerType serverType) {
        return emitters.entrySet().stream()
                .filter(entry -> entry.getValue().getServer().equals(serverType))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

	public Optional<Emitter> findById(Long clientId) {
		return Optional.of(emitters.get(clientId));
		
	}
}
