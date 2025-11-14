package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dto.HelloResponseDto;

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
