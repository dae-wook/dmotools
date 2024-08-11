package com.daesoo.dmotools.alarm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final EmitterRepository emitterRepository;


    private static final Long DEFAULT_TIMEOUT = 600L * 1000 * 60;

    public SseEmitter subscribe() {
        SseEmitter emitter = createEmitter();


        sendToClient(emitter, "EventStream Created.", "sse 접속 성공");
        return emitter;
    }
    
    public List<SseEmitter> getSubscribers() {
    	
    	return emitterRepository.findAll();
    }
    
    public void notify(Object data, String comment, String type) {
        notifyAll(data, comment, type);
    }

    private void notifyAll(Object data, String comment, String type) {
        List<SseEmitter> deadEmitters = new ArrayList<>();
        
        for (SseEmitter emitter : emitterRepository.findAll()) {
            try {
                emitter.send(SseEmitter.event()
                        .name(type)
                        .data(data)
                        .comment(comment));
            } catch (IOException e) {
                // Handle the emitter that failed to send data
                deadEmitters.add(emitter);
            }
        }
        
        // Remove and clean up dead emitters after processing
        deadEmitters.forEach(emitterRepository::delete);
    }


    private void sendToClient(SseEmitter emitter, Object data, String comment) {
        try {
            emitter.send(SseEmitter.event()
                    .name("sse")
                    .data(data)
                    .comment(comment));
        } catch (IOException e) {
        	e.printStackTrace();
            emitterRepository.delete(emitter);
            emitter.completeWithError(e);
        }
    }


    private SseEmitter createEmitter() {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(emitter);

        emitter.onCompletion(() -> emitterRepository.delete(emitter));
        emitter.onTimeout(() -> emitterRepository.delete(emitter));

        return emitter;
    }
}
