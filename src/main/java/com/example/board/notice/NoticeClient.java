package com.example.board.notice;

import java.time.Duration;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * 사내 공지 시스템에서 게시판 상단에 띄울 공지를 가져온다.
 */
@Component
public class NoticeClient {

	private final RestClient restClient;

	public NoticeClient(@Value("${notice.base-url}") String baseUrl) {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(Duration.ofSeconds(3));
		factory.setReadTimeout(Duration.ofSeconds(3));
		this.restClient = RestClient.builder().baseUrl(baseUrl).requestFactory(factory).build();
	}

	public List<Notice> fetch() {
		return restClient.get()
			.uri("/api/notices?board=general")
			.retrieve()
			.body(new ParameterizedTypeReference<List<Notice>>() {
			});
	}

}
