package com.example.demo.domain.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.comment.dto.CommentCreateRequest;
import com.example.demo.domain.comment.dto.CommentResponse;
import com.example.demo.domain.comment.dto.CommentUpdateRequest;
import com.example.demo.domain.comment.entity.Comment;
import com.example.demo.domain.comment.repository.CommentRepository;
import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.repository.PostRepository;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.global.exception.NotFoundException;
import com.example.demo.global.exception.UnauthorizedException;
import com.example.demo.global.redis.PostRedisService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final CommentRepository commentRepository;
	private final PostRedisService postRedisService;

	public List<CommentResponse> getComments(Long postId) {
		
		Post post = postRepository.findById(postId)
				.orElseThrow( () -> new NotFoundException("게시글을 찾을 수 없습니다.") );
		
		return commentRepository.findByPost(post)
				.stream()
				.map( c-> CommentResponse.builder()
										.id(c.getId())
										.content(c.getContent())
										.authorId(c.getUser().getId())
										.authorName(c.getUser().getUsername())
										.build() )
				.toList();
	}

	@Transactional
	public CommentResponse createComment(Long postId, Long userId, CommentCreateRequest request) {
		
		
		Post post = postRepository.findById(postId)
				.orElseThrow( () -> new NotFoundException("게시글을 찾을 수 없습니다."));
		
		User user = userRepository.findById(userId)
				.orElseThrow( () -> new NotFoundException("유저가 존재하지 않습니다."));
		
		Comment comment = Comment.builder()
				.post(post)
				.user(user)
				.content(request.getContent())
				.build();
		commentRepository.save(comment);
		deletePostAtRedis(comment.getPost().getId());
		
		return CommentResponse.builder()
				.id(comment.getId())
				.content(comment.getContent())
				.authorId(user.getId())
				.authorName(user.getUsername())
				.build();
	}

	@Transactional
	public CommentResponse updateComment(Long id, Long userId, CommentUpdateRequest request) {
		
		Comment comment = commentRepository.findById(id)
				.orElseThrow( () -> new NotFoundException("댓글을 찾을 수 없습니다.") );
		
		if ( ! comment.getUser().getId().equals(userId) ) {
			throw new UnauthorizedException("본인 댓글만 수정할 수 있습니다.");
		}
		
		comment.setContent(request.getContent());
		commentRepository.save(comment);
		deletePostAtRedis(comment.getPost().getId());
		
		return CommentResponse.builder()
				.id(comment.getId())
				.content(comment.getContent())
				.authorId(comment.getUser().getId())
				.authorName(comment.getUser().getUsername())
				.build();
	}

	@Transactional
	public void deleteComment(Long id, Long userId) {
		
		Comment comment = commentRepository.findById(id)
				.orElseThrow( () -> new NotFoundException("댓글이 존재하지 않습니다.") );
		
		if ( ! comment.getUser().getId().equals(userId)  ) {
			throw new UnauthorizedException("본인이 작성한 댓글만 삭제할 수 있습니다.");
		}
		
		commentRepository.delete(comment);
		deletePostAtRedis(comment.getPost().getId());
	}

	
	private void deletePostAtRedis(Long postId) {
		try {
			postRedisService.deletePost(postId);
		} catch (Exception e) { }
	}
	
	
}
