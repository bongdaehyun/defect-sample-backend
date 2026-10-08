package com.example.board.notice;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoticeController {

	private final NoticeClient noticeClient;

	public NoticeController(NoticeClient noticeClient) {
		this.noticeClient = noticeClient;
	}

	@GetMapping("/api/notices")
	public List<Notice> list() {
		return noticeClient.fetch();
	}

}
