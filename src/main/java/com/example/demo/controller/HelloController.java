package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.HelloResponseDto;
import com.example.demo.service.HelloService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HelloController {
	
	/*
	 * @RequiredArgsConstructor
	 * 생성자 자동 생성
	 */
	
	private final HelloService helloService;

	@GetMapping("/hello")
	public String SayHello() {
		return "Hello Spring Boot!";
	}
	
	@GetMapping("/hello-json")
	public HelloResponseDto helloJson(@RequestParam String name) {
		return helloService.getHellomessage(name);
	}
	
}
