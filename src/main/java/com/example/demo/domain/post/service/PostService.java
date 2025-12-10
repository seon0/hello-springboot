package com.example.demo.domain.post.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.basic.dto.ResponseDto;
import com.example.demo.domain.like.repository.LikeRepository;
import com.example.demo.domain.post.dto.PostDetailResponse;
import com.example.demo.domain.post.dto.PostRequestDto;
import com.example.demo.domain.post.dto.PostResponse;
import com.example.demo.domain.post.dto.PostUpdateRequest;
import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.repository.PostRepository;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.global.jwt.TokenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
	
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final LikeRepository likeRepository;
	private final TokenService tokenService;
	
	public Post createPost(String token, PostRequestDto dto) {
		User user = tokenService.getUserFromToken(token);
		
		Post post = Post.builder()
							.title(dto.getTitle())
							.content(dto.getContent())
							.writer(user)
							.build();
		
		postRepository.save(post);
		
//		return ResponseDto.success("게시글이 등록되었습니다.");
		return post;
	}
	
	
	public ResponseDto<?> getPost(Long id, Long userId) {
		Post post = findPostByIdOrThrow(id);

		long likeCount = likeRepository.countByPost(post);
		boolean likedByMe = false;
		if ( userId != null ) {
			likedByMe = likeRepository.existsByUserAndPost(User.builder().id(userId).build(), post);
		}
		
		return ResponseDto.success(PostDetailResponse.from(post, likeCount, likedByMe));
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
	public ResponseDto<?> updatePost(String token, Long id, PostUpdateRequest dto) {
		User user = tokenService.getUserFromToken(token);
		
		Post post = findPostByIdOrThrow(id);
		
		validateOwner(user.getId(), post);
		
		post.setTitle(dto.getTitle());
		post.setContent(dto.getContent());

		
		postRepository.save(post);
		
		long likeCount = likeRepository.countByPost(post);
		boolean likedByMe = likeRepository.existsByUserAndPost(user, post);
		return ResponseDto.success(PostDetailResponse.from(post, likeCount, likedByMe));
	}
	

	@Transactional
	public ResponseDto<?> deletePost(String token, Long id) {
		User user = tokenService.getUserFromToken(token);
		
		Post post = findPostByIdOrThrow(id);
		validateOwner(user.getId(), post);
		
		postRepository.delete(post);
		
		return ResponseDto.success("삭제 완료");
	}
	
	

	
	public Post findPostByIdOrThrow(Long id) {
		return postRepository.findById(id)
		.orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
	}

	
	public void validateOwner(Long userId, Post post) {
		if ( ! post.getWriter().getId().equals(userId) ) {
			throw new RuntimeException("게시글 작성자만 수정/삭제할 수 있습니다.");
		}
	}


}
