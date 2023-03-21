# Local에서 bootRun하는 방법
bootRun하기 전에 `api/main/resources` 경로에 application.yml 파일을 작성해야 합니다.
### application.yml
```yaml
# H2 설정
spring:
  h2:
    console:
      enabled:true
      path:/h2-console
  datasource:
    driver-class-name: org.h2.Driver
    url: jdbc:h2:mem:test
    username: root
    password: password
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect

naver:
  client-id: naver 어플리케이션 client id 기입
  client-secret: naver 어플리케이션 client secret 기입

kakao:
  rest-api-key: kakao 어플리케이션 rest-api-key 기입
```

# 과제 기능 요구사항 수행 여부
✅ 블로그 검색

✅ 인기 검색어 목록 조회

✅ 멀티모듈 구성 (API, CORE)

✅ 대용량 트래픽 고려 (WebFlux)

⬜️ 저장되어 있는 데이터가 많음을 염두에 둔 구현

✅ 동시성 이슈가 발생할 수 있는 부분을 염두에 둔 구현 (대용량 트래픽 고려 안됨)

✅ 카카오 블로그 검색 API에 장애가 발생한 경우, 네이버 블로그 검색 API를 통해 데이터 제공

# API 명세

- 블로그 검색 기능
- 인기 검색어 조회 기능

## 블로그 검색 기능
다음 검색 서비스에서 질의어로 블로그 포스트를 검색합니다.

다음 검색 서비스 제공에 문제가 발생할 경우 네이버 블로그 포스트가 제공될 수 있습니다.

### 기본 정보
```
GET /v1/blog/search

Host: localhost:8080
Content-Type: application/json

```

### Request
| Name | Type | Description | Required |
|---|---|---|---|
| `blogUrl`  |  String |  검색을 원하는 블로그 주소 | X |
|  `keyword` |  String | 검색을 원하는 질의어  | O |
|  `sort` |  String |  결과 문서 정렬 방식, ACCURACY(정확도순) 또는 RECENCY(최신순), 기본 값 ACCURACY | X |
|  `page` |  Integer |  결과 페이지 번호, 1~50 사이의 값, 기본 값 1 | X |
|  `size` | Integer  |  한 페이지에 보여질 문서 수, 1~80 사이의 값, 기본 값 30 | X |


### Response

| Name | Type | Description |
|---|---|---|
| `totalCount`  |  String |  검색된 문서 수 |
|  `contents[].title` |  String | 블로그 글 제목  |
|  `contents[].blogName` |  String | 블로그의 이름 |
|  `contents[].blogPostUrl` |  String | 블로그 글 URL |
|  `contents[].description` | String  | 블로그 글 요약 |
|  `contents[].postDate` | String  | 블로그 글 작성시간(yyyy-MM-dd) |

## 인기 검색어 조회 기능

인기 검색어 상위 10개를 조회합니다.

### 기본 정보
```
GET /v1/blog/popular-keyword

Host: localhost:8080
Content-Type: application/json

```

해당 API는 request parameter 혹은 request body가 존재하지 않습니다.

### Response

| Name | Type | Description |
|---|---|---|
| `totalCount`  |  String |  조회된 상위 키워드 수 |
|  `popularKeywords[].keyword` |  String | 키워드  |
|  `popularKeywords[].searchCount` |  String | 검색 수 |


## 오류 코드
| 오류코드 | HTTP 상태코드  | 오류 메세지  |
|---|---|---|
| EC0001  |  400 |  잘못된 요청입니다. |
| EC0002  |  400 |  요청 값을 확인해주세요. |
| EC0003  |  404 |  요청을 찾을 수 없습니다. |
| ES0001  |  500 |  서버에 문제가 발생했습니다. |


## 외부 라이브리 및 오픈소스 사용 목적 정리
- `spring-boot-starter-data-jpa`: JPA 사용
- `spring-boot-starter-webflux`: 비동기 논블로킹 방식을 통해 적은 리소스로 높은 성능을 위해 사용
- 코틀린 관련 라이브러리
    - `reactor-kotlin-extensions`
    - `kotlin-reflect`
    - `kotlinx-coroutines-reactor`
- `jackson-module-kotlin`: Json 변환/파싱을 위해 사용
- 테스트 관련 라이브러리
    - `io.mockk:mockk:1.9.3`
    - `com.ninja-squad:springmockk:3.1.1`
- `com.h2database:h2`: h2 데이터베이스 사용
- `spring-boot-configuration-processor`: 프로퍼티 설정을 위해 사용
