package com.example.demo.global.redis.util;

import java.time.Duration;

public class RedisTTL {
	
	public static final Duration POST_DETAIL = Duration.ofMinutes(10);
	public static final Duration POST_LIST = Duration.ofMinutes(3);
	public static final Duration LIST_COUNT = Duration.ofSeconds(60);

}
