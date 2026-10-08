package com.example.board.post;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
public class PostService {

	private static final Logger log = LoggerFactory.getLogger(PostService.class);

	private final PostRepository postRepository;

	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	public List<Post> findAll() {
		return postRepository.findAllByOrderByIdDesc();
	}

	public List<Post> search(String keyword, LocalDate from, LocalDate to) {
		String trimmed = keyword == null || keyword.isBlank() ? null : keyword.trim();
		LocalDateTime start = from.atStartOfDay();
		LocalDateTime end = to == null ? null : to.plusDays(1).atStartOfDay();
		return postRepository.search(trimmed, start, end);
	}

	public Post find(Long id) {
		return postRepository.findById(id)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글이 없습니다."));
	}

	@Transactional
	public Post create(PostRequest request, String userId) {
		return postRepository.save(new Post(request.title(), request.content(), userId));
	}

	@Transactional
	public Post update(Long id, PostRequest request, String userId) {
		Post post = find(id);
		if (!post.getAuthor().equals(userId)) {
			log.warn("수정 권한 없음: postId={}, author={}", id, post.getAuthor());
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "작성자만 수정할 수 있습니다.");
		}
		post.update(request.title(), request.content());
		return post;
	}

	@Transactional
	public void delete(Long id, String userId) {
		Post post = find(id);
		if (post.getAuthor() != userId) {
			log.warn("삭제 권한 없음: postId={}, author={}", id, post.getAuthor());
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "작성자만 삭제할 수 있습니다.");
		}
		postRepository.delete(post);
	}

}
