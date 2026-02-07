package com.example.demo.global.redis;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.example.demo.domain.post.dto.PostDetailResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class RedisConfig {
	
//	@Value("${spring.data.redis.host}")
//	private String redisHost;
//	
//	@Value("${spring.data.redis.port}")
//	private int redisPort;
	
	@Bean(name = "redisObjectMapper")
	public ObjectMapper redisObjectMapper() {
//		BasicPolymorphicTypeValidator validator = BasicPolymorphicTypeValidator.builder()
//				.allowIfSubType(Object.class)
//				.build();
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//		objectMapper.activateDefaultTyping(validator, ObjectMapper.DefaultTyping.NON_FINAL);
		return objectMapper;
	}
	
//	@Bean
//	public RedisConnectionFactory redisConnectionFactory() {
//		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(redisHost, redisPort);
//		return new LettuceConnectionFactory(configuration);
//	}
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory
			, @Qualifier("redisObjectMapper") ObjectMapper objectMapper
		) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(factory);
		
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));
		
		return template;
	}
	
	@Bean
	public RedisTemplate<String, PostDetailResponse> postRedisTemplate(RedisConnectionFactory factory
			, @Qualifier("redisObjectMapper") ObjectMapper objectMapper
		) {
		RedisTemplate<String, PostDetailResponse> template = new RedisTemplate<>();
		template.setConnectionFactory(factory);
		
		template.setKeySerializer(new StringRedisSerializer());
		
		Jackson2JsonRedisSerializer<PostDetailResponse> serializer = new Jackson2JsonRedisSerializer<>(PostDetailResponse.class);
		serializer.setObjectMapper(objectMapper);
		template.setValueSerializer(serializer);
		
		return template;
	}

	
}
