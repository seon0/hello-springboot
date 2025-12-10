package com.example.demo.basic.service;

import org.springframework.stereotype.Service;

import com.example.demo.basic.dto.HelloResponseDto;

@Service
public class HelloService {
	
	public HelloResponseDto getHellomessage(String name) {
		return new HelloResponseDto(
				"Hello Spring!",
				name, 
				1
		);
	}

}
