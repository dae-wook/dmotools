package com.daesoo.dmotools.alarm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.daesoo.dmotools.common.dto.ServerType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final EmitterRepository emitterRepository;


    private static final Long DEFAULT_TIMEOUT = 600L * 1000 * 60;

    public SseEmitter subscribe(ServerType serverType) {
        Emitter emitter = createEmitter(serverType);


        sendToClient(emitter, "sub", emitter, "sse 접속 성공");
        return emitter.getEmitter();
    }
    
    public boolean cancel(Long clientId) {
    	
    	Emitter emitter = emitterRepository.findById(clientId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 emitter"));
    	sendToClient(emitter, "disub", emitter, "SSE Connection closed");
    	emitterRepository.delete(clientId);
    	return true;
    }
    
    
    
    public Map<Long, Emitter> getSubscribers() {
    	
    	return emitterRepository.findAll();
    }
    
    public void notify(Object data, String comment, String type, ServerType serverType) {
        notifyAll(data, comment, type, serverType);
    }

    private void notifyAll(Object data, String comment, String type, ServerType serverType) {
        Map<Long, Emitter> emitters = emitterRepository.findAllByServerType(serverType);
        List<Long> deadEmitters = new ArrayList<>();
        for (Long clientId : emitters.keySet()) {
        	
            try {
                emitters.get(clientId).getEmitter().send(SseEmitter.event()
                        .name(type)
                        .data(data)
                        .comment(comment));
            } catch (IOException e) {
                // Handle the emitter that failed to send data
                deadEmitters.add(clientId);
            }
        }
        
        // Remove and clean up dead emitters after processing
        deadEmitters.forEach(emitterRepository::delete);
    }


    private void sendToClient(Emitter emitter, String type, Object data, String comment) {
        try {
            emitter.getEmitter().send(SseEmitter.event()
                    .name(type)
                    .data(data)
                    .comment(comment));
        } catch (IOException e) {
        	e.printStackTrace();
            emitterRepository.delete(emitter.getClientId());
            emitter.getEmitter().completeWithError(e);
        }
    }


    private Emitter createEmitter(ServerType serverType) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        Long clientId = emitterRepository.save(emitter, serverType);
        emitter.onCompletion(() -> emitterRepository.delete(clientId));
        emitter.onTimeout(() -> emitterRepository.delete(clientId));
        
        return Emitter.create(clientId, emitter, serverType);
    }

	public boolean change(Long clientId, ServerType server) {
		
		Emitter emitter = emitterRepository.findById(clientId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 emitter"));
		ServerType oldServer = emitter.getServer();
		emitter.changeServer(server);
		
		emitterRepository.update(emitter);
		
		sendToClient(emitter, "server-changed", emitter, oldServer + " => " + emitter.getServer() + "서버 변경");
		
		return true;
	}
}
