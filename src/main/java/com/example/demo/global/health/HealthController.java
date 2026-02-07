package com.example.demo.global.health;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HealthController {
	
	private final HealthService healthService;

	@GetMapping("/health")
	public ResponseEntity<HealthResponse> health() {
		HealthResponse response = healthService.check();
		return response.isUp()
					? ResponseEntity.ok(response)
					: ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
	}
}
