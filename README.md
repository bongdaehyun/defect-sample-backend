# defect-sample-backend

결함 처리 흐름 자동화 프로젝트에서 쓰는 샘플 게시판의 백엔드입니다. 프론트엔드는 [defect-sample-frontend](https://github.com/bongdaehyun/defect-sample-frontend)에 있습니다.

- Spring Boot 4.1, Java 25, H2(메모리)
- 실행: `./gradlew bootRun` (포트 8080)
- 요청한 사용자는 `X-User-Id` 헤더로 받습니다. 로그인은 없습니다.
