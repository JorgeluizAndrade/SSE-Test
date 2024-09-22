package com.test.sse.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class SseService {
	private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

	public void addEmitter(SseEmitter emitter) {
		emitters.add(emitter);
		emitter.onCompletion(() -> emitters.remove(emitter));
		emitter.onTimeout(() -> emitters.remove(emitter));
	}

	@Scheduled(fixedRate = 1000)
	public void sendEmitters() {
		for (SseEmitter emitter : emitters) {
			try {
				String pattern = "HH:mm:ss";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				String date = simpleDateFormat.format(new Date());
				emitter.send(date);
			} catch (IOException e) {
				emitter.complete();
				emitters.remove(emitter);
			}

		}
	}

}
