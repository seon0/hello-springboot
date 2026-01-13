package com.example.demo.global.redis.util;

public class RedisKey {
	
	private RedisKey() {}
	
	public static String postDetail(Long postId) {
		return "post::detail::" + postId;
	}

	public static String postLikeCount(Long postId) {
		return "post::like::count::" + postId;
	}

	public static String postListPage(int page) {
		return "post::list::page::" + page;
	}

}
