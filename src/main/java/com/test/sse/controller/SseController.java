package com.test.sse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.test.sse.service.SseService;

@RestController
public class SseController {
	@Autowired
	SseService sseService;

	@GetMapping("/sse")
	public SseEmitter subs() {
		SseEmitter emitter = new SseEmitter();
		sseService.addEmitter(emitter);
		return emitter;
	}
}
