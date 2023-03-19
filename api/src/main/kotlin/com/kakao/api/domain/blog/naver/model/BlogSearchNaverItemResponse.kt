package com.kakao.api.domain.blog.naver.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class BlogSearchNaverItemResponse(
    val title: String, // 블로그 포스트의 제목
    @JsonProperty("link")
    val blogPostUrl: String, // 블로그 포스트의 URL
    val description: String,
    @JsonProperty("bloggername")
    val bloggerName: String, // 블로그 포스트가 있는 블로그의 이름
    @JsonProperty("bloggerlink")
    val blogUrl: String, // 블로그 포스트가 있는 블로그의 주소
    @JsonProperty("postdate")
    val postDate: LocalDateTime, // 블로그 포스트가 작성된 날짜
)