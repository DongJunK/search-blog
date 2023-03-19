package com.kakao.api.domain.blog.kakao.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class BlogSearchKakaoDocumentResponse(
    val title: String, // 블로그 글 제목
    val contents: String, // 블로그 글 요약
    val url: String, // 블로그 글 URL
    @JsonProperty("blogname")
    val blogName: String, // 블로그의 이름
    val thumbnail: String, // 검색 시스템에서 추출한 대표 미리보기 이미지 URL
    val datetime: LocalDateTime, // 블로그 글 작성시간
)