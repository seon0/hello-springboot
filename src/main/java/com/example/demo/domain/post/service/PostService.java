package com.example.demo.domain.post.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.like.repository.LikeRepository;
import com.example.demo.domain.post.dto.PostDetailResponse;
import com.example.demo.domain.post.dto.PostRequestDto;
import com.example.demo.domain.post.dto.PostResponse;
import com.example.demo.domain.post.dto.PostSearchCondition;
import com.example.demo.domain.post.dto.PostUpdateRequest;
import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.repository.PostQueryRepository;
import com.example.demo.domain.post.repository.PostRepository;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.service.UserService;
import com.example.demo.dto.ResponseDto;
import com.example.demo.global.redis.PostRedisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
	
	private final PostRepository postRepository;
	private final LikeRepository likeRepository;
	private final PostQueryRepository postQueryRepository;
	private final PostRedisService postRedisService;
	private final UserService userService;

	@Transactional
	public Post createPost(Long userId, PostRequestDto dto) {
		User user = userService.getUserById(userId);
		
		Post post = Post.builder()
							.title(dto.getTitle())
							.content(dto.getContent())
							.user(user)
							.build();
		
		postRepository.save(post);
		
//		return ResponseDto.success("게시글이 등록되었습니다.");
		return post;
	}
	
	
	@Transactional(readOnly = true)
	public ResponseDto<?> getPost(Long id, Long userId) {
		
		try {
			PostDetailResponse cachedPost = postRedisService.getPost(id);
			if ( cachedPost != null ) {
//			return ResponseDto.success( PostDetailResponse.from(cachedPost, (long)cachedPost.getLikeCount(), false) );
				return ResponseDto.success(cachedPost);
			}
		} catch (Exception e) {
			log.warn("[PostService - getPost] Redis get failed. fallback to DB. postId={}", id);
		}
		
		
		Post post = findPostByIdOrThrow(id);

		long likeCount = likeRepository.countByPost(post);
		post.setLikeCount((int) likeCount);
		boolean likedByMe = false;
		if ( userId != null ) {
			likedByMe = likeRepository.existsByUserAndPost(User.builder().id(userId).build(), post);
		}
		
		PostDetailResponse res = PostDetailResponse.from(post, likeCount, likedByMe);
		try {
			postRedisService.savePost(res);
		} catch (Exception e) {
			log.warn("[PostService - getPost] Redis save failed. ignore. postId={}", id);
		}
		
		return ResponseDto.success(res);
	}

	public ResponseDto<?> getPosts() {
		List<Post> posts = postRepository.findAll();
		
		return ResponseDto.success(
				posts.stream()
					.map(PostResponse::from)
					.toList()
		);
	}
	
	@Transactional
	public ResponseDto<?> updatePost(Long userId, Long id, PostUpdateRequest dto) {
		User user = userService.getUserById(userId);
		
		Post post = findPostByIdOrThrow(id);
		
		validateOwner(user.getId(), post);
		
		post.setTitle(dto.getTitle());
		post.setContent(dto.getContent());

		
		postRepository.save(post);
		try {
			postRedisService.deletePost(post.getId());
		} catch (Exception e) { }
		
		long likeCount = likeRepository.countByPost(post);
		boolean likedByMe = likeRepository.existsByUserAndPost(user, post);
		return ResponseDto.success(PostDetailResponse.from(post, likeCount, likedByMe));
	}
	

	@Transactional
	public ResponseDto<?> deletePost(Long userId, Long id) {
		User user = userService.getUserById(userId);
		
		Post post = findPostByIdOrThrow(id);
		validateOwner(user.getId(), post);
		
		postRepository.delete(post);
		try {
			postRedisService.deletePost(post.getId());
		} catch (Exception e) { }
		
		return ResponseDto.success("삭제 완료");
	}
	
	

	
	public Post findPostByIdOrThrow(Long id) {
		return postRepository.findById(id)
		.orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
	}

	
	public void validateOwner(Long userId, Post post) {
		if ( ! post.getUser().getId().equals(userId) ) {
			throw new RuntimeException("게시글 작성자만 수정/삭제할 수 있습니다.");
		}
	}

	
//	public Page<PostResponse> search(PostSearchCondition cond, Pageable pageable) {
//		Page<Post> posts = postQueryRepository.search(cond, pageable);
//		return posts.map(PostResponse::from);
//	}
//	
//
//	public List<Post> searchPosts(PostSearchCondition condition, Long userId) {
//		return postQueryRepository.searchPosts(condition, userId);
//	}

	public Page<PostResponse> searchFinal(PostSearchCondition cond, Pageable pageable, Long userId) {
		return postQueryRepository
				.searchFinal(cond, userId, pageable)
				.map(PostResponse::from);
	}
}
