package com.example.demo.domain.post.repository;

//import org.aspectj.weaver.ast.Var;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.like.entity.QLike;
import com.example.demo.domain.post.dto.PostSearchCondition;
import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.entity.QPost;
import com.example.demo.domain.user.entity.QUser;
//import com.example.demo.dto.SearchCondition;
//import com.example.demo.dto.SearchOrder;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
//import com.querydsl.core.types.Order;
//import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;


//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostQueryRepository {

	private final JPAQueryFactory query;
	
	/*
	public Page<Post> search(PostSearchCondition cond, Pageable pageable) {
		QPost post = QPost.post;
		QUser user = QUser.user;
		List<Post> result = query
				.selectFrom(post)
				.leftJoin(post.user, user).fetchJoin()
				.where(
						titleContains(post, cond.getTitle()),
						contentContains(post, cond.getContent()),
						nicknameEq(post, cond.getNickname()),
						dateBetween(post, cond.getFromDate(), cond.getToDate())
				)
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.orderBy(post.id.desc())
				.fetch();
				
		long total =query
				.select(post.count())
				.from(post)
				.where( 
						titleContains(post, cond.getTitle()),
						contentContains(post, cond.getContent()),
						nicknameEq(post, cond.getNickname()),
						dateBetween(post, cond.getFromDate(), cond.getToDate())
				)
				.fetchOne();
				
		return new PageImpl<>(result, pageable, total);
	}

	
	private BooleanExpression titleContains(QPost post,String title) {
		return (title != null && ! title.isBlank() )
					? post.title.containsIgnoreCase(title)
					: null;
	}
	
	private BooleanExpression contentContains(QPost post,String content) {
		return (content != null && ! content.isBlank() )
					? post.content.containsIgnoreCase(content)
					: null;
	}

	private BooleanExpression nicknameEq(QPost post,String nickname) {
		return (nickname != null && ! nickname.isBlank() )
					? post.user.nickname.eq(nickname)
					: null;
	}

	private BooleanExpression dateBetween(QPost post,LocalDate from, LocalDate to) {
		if ( from == null && to == null ) return null;
		
		LocalDateTime start = ( from != null ) 
												? from.atStartOfDay() 
												: LocalDate.now().minusYears(20).atStartOfDay();
		
		LocalDateTime end = ( to != null )
												? to.atTime(23, 59, 59)
												: LocalDate.now().atTime(23, 59, 59);
		
		return post.createdAt.between(start, end);
	}
	*/
	
	

	/*
	public List<Post> searchPosts(PostSearchCondition cond, Long userId) {
		QPost post = QPost.post;
		QUser user = QUser.user;
		BooleanBuilder builder = new BooleanBuilder();
		

//		---------- 기본 조건 ----------
		if ( cond.getOnlyActive() == null || cond.getOnlyActive() ) {
//			deleted == false 조건 넣고 싶다면 여기에서!
//			builder.and(post.deleted.eq(false));
		}
		
		// 제목 검색
		if ( cond.getTitle() != null  && ! cond.getTitle().isBlank() ) {
			builder.and( post.title.containsIgnoreCase(cond.getTitle()) );
		}

		// 내용 검색
		if ( cond.getContent() != null  && ! cond.getContent().isBlank() ) {
			builder.and( post.content.containsIgnoreCase(cond.getContent()) );
		}
		
		// 작성자 검색
		if ( cond.getNickname() != null && ! cond.getNickname().isBlank() ) {
			builder.and( post.user.nickname.containsIgnoreCase(cond.getNickname()) );
		}
		
		// 통합 검색
		if ( cond.getKeyword() != null && ! cond.getKeyword().isBlank() ) {
			String k = cond.getKeyword();
			builder.and(
					post.title.containsIgnoreCase(k)
								.or(post.content.containsIgnoreCase(k))
								.or(post.user.nickname.containsIgnoreCase(k))
			);
		}
		
		// 내가 쓴 글만
		if ( cond.getOnlyMine() != null && cond.getOnlyMine() ) {
			builder.and(post.user.id.eq(userId));
		}
		
		// 정렬
		List<OrderSpecifier<?>> orders = createOrderSpecifiers(cond.getOrder(), post, user);
		
		return query.selectFrom(post)
						.leftJoin(post.user, user).fetchJoin()
						.where(builder)
						.orderBy(orders.toArray(new OrderSpecifier[0]))
						.fetch();
	}
	
	
	private List<OrderSpecifier<?>> createOrderSpecifiers(SearchOrder order, QPost post, QUser user) {
		List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
		
		if ( order == null ) {
			orderSpecifiers.add(post.createdAt.desc());
			return orderSpecifiers;
		}
		
		Order direction = order.getDirection().equalsIgnoreCase("asc") ?
				Order.ASC : Order.DESC;
		
		switch ( order.getField() ) {
		
		case "title":
			orderSpecifiers.add(new OrderSpecifier<>(direction, post.title));
			break;
			
		case "nickname":
			orderSpecifiers.add(new OrderSpecifier<>(direction, user.nickname));
			break;
			
		case "createdAt":
		default:
			orderSpecifiers.add(new OrderSpecifier<>(direction, post.createdAt));
			break;
		}
		
		return orderSpecifiers;
	}
	 */
	
	
	public Page<Post> searchFinal(PostSearchCondition cond, Long userId, Pageable pageable) {
		QPost post = QPost.post;
		QUser user = QUser.user;
		QLike like = QLike.like;
		
		BooleanBuilder where = new BooleanBuilder();
		if ( cond.getTitle() != null  && ! cond.getTitle().isBlank() ) {
			where.and( post.title.containsIgnoreCase(cond.getTitle()) );
		}
		if ( cond.getContent() != null  && ! cond.getContent().isBlank() ) {
			where.and( post.content.containsIgnoreCase(cond.getContent()) );
		}
		if ( cond.getNickname() != null && ! cond.getNickname().isBlank() ) {
			where.and( post.user.nickname.containsIgnoreCase(cond.getNickname()) );
		}
		if ( cond.getKeyword() != null && ! cond.getKeyword().isBlank() ) {
			String k = cond.getKeyword();
			where.and(
					post.title.containsIgnoreCase(k)
								.or(post.content.containsIgnoreCase(k))
								.or(post.user.nickname.containsIgnoreCase(k))
			);
		}
		if ( Boolean.TRUE.equals(cond.getOnlyMine()) ) {
			where.and(post.user.id.eq(userId));
		}
		
		if ( Boolean.TRUE.equals(cond.getOnlyLiked()) ) {
			where.and(
					post.id.in(
							query.select(like.post.id)
							.from(like)
							.where(like.user.id.eq(userId))
					)
			);
		}
		
		OrderSpecifier<?> order = switch ( cond.getSort() ) {
			case "views" -> post.viewCount.desc();
			case "likes" -> post.likeCount.desc();
			default -> post.createdAt.desc();
		};
		
		List<Post> content = query
				.selectFrom(post)
				.leftJoin(post.user, user).fetchJoin()
				.where(where)
				.orderBy(order)
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		
		Long total = query
				.select(post.count())
				.from(post)
				.where(where)
				.fetchOne();
		
		return new PageImpl<>(content, pageable, total);
	}
	
	
	
}
