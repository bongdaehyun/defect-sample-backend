package com.example.board.common;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 요청마다 사용자와 API 경로를 로그 MDC에 넣고, 처리 결과를 한 줄로 남긴다.
 */
@Component
public class RequestLogFilter extends OncePerRequestFilter {

	private static final Logger log = LoggerFactory.getLogger(RequestLogFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		String user = request.getHeader("X-User-Id");
		String query = request.getQueryString();
		String path = request.getRequestURI() + (query == null ? "" : "?" + query);
		MDC.put("user", user == null ? "-" : user);
		MDC.put("request", request.getMethod() + " " + path);
		long started = System.currentTimeMillis();
		try {
			chain.doFilter(request, response);
		}
		finally {
			log.info("{} ({}ms)", response.getStatus(), System.currentTimeMillis() - started);
			MDC.clear();
		}
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return !request.getRequestURI().startsWith("/api/");
	}

}
