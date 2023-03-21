package com.kakao.api.domain.kakao.model

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoBlogSearchDocumentResponse(
    val title: String, // 블로그 글 제목
    val contents: String, // 블로그 글 요약
    @JsonProperty("url")
    val blogPostUrl: String, // 블로그 글 URL
    @JsonProperty("blogname")
    val blogName: String, // 블로그의 이름
    val thumbnail: String, // 검색 시스템에서 추출한 대표 미리보기 이미지 URL
    val datetime: String, // 블로그 글 작성시간
)