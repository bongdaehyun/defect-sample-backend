package com.example.board.post;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@GetMapping
	public List<Post> list() {
		return postService.findAll();
	}

	@GetMapping("/search")
	public List<Post> search(@RequestParam(required = false) String keyword,
			@RequestParam(required = false) LocalDate from,
			@RequestParam(required = false) LocalDate to) {
		return postService.search(keyword, from, to);
	}

	@GetMapping("/{id}")
	public Post get(@PathVariable Long id) {
		return postService.find(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Post create(@Valid @RequestBody PostRequest request, @RequestHeader("X-User-Id") String userId) {
		return postService.create(request, userId);
	}

	@PutMapping("/{id}")
	public Post update(@PathVariable Long id, @Valid @RequestBody PostRequest request,
			@RequestHeader("X-User-Id") String userId) {
		return postService.update(id, request, userId);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id, @RequestHeader("X-User-Id") String userId) {
		postService.delete(id, userId);
	}

}
