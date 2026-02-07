package com.example.demo.global.health;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HealthResponse {
	
	private String status;
	private String db;
	private String redis;
	
	public boolean isUp() {
		return "UP".equals(status);
	}
	
	public static HealthResponse up(boolean dbUp, boolean redisUp) {
		return new HealthResponse(
				( dbUp && redisUp ) ? "UP" : "DOWN",
				dbUp ? "UP" : "DOWN", 
				redisUp ? "UP" : "DOWN"
		);
	}

}
