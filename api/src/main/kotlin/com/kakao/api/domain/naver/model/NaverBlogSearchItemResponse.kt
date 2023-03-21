package com.kakao.api.domain.naver.model

import com.fasterxml.jackson.annotation.JsonProperty

data class NaverBlogSearchItemResponse(
    val title: String, // 블로그 포스트의 제목
    @JsonProperty("link")
    val blogPostUrl: String, // 블로그 포스트의 URL
    val description: String, // 블로그 포스트의 내용을 요약한 패시지 정보
    @JsonProperty("bloggername")
    val bloggerName: String, // 블로그 포스트가 있는 블로그의 이름
    @JsonProperty("bloggerlink")
    val blogUrl: String, // 블로그 포스트가 있는 블로그의 주소
    @JsonProperty("postdate")
    val postDate: String, // 블로그 포스트가 작성된 날짜
)