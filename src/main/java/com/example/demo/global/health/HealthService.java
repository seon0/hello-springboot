package com.example.demo.global.health;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class HealthService {
	
	private final DataSource dataSource;
	private final RedisConnectionFactory redisConnectionFactory;
	
	public HealthResponse check() {
		boolean dbUp = checkDb();
		boolean redisUp = checkRedis();
		
		return HealthResponse.up(dbUp, redisUp);
	}
	
	
	private boolean checkDb() {
		try ( Connection conn = dataSource.getConnection() ){
			return conn.isValid(1);
		} catch (Exception e) {
			log.warn("[HealthService-checkDb] DB health check failed", e);
			return false;
		}
	}
	
	private boolean checkRedis() {
		try ( RedisConnection conn = redisConnectionFactory.getConnection() ){
			return conn.ping() != null;
		} catch (Exception e) {
			log.warn("[HealthService-checkRedis] Redis health check failed", e);
			return false;
		}
	}

}
