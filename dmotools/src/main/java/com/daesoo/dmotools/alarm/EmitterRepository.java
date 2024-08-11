package com.daesoo.dmotools.alarm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmitterRepository {

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public void save(SseEmitter emitter) {
        emitters.add(emitter);
    }

    public void delete(SseEmitter emitter) {
        emitters.remove(emitter);
    }
    
    public List<SseEmitter> findAll() {
    	return new ArrayList<>(emitters);
    }
}
