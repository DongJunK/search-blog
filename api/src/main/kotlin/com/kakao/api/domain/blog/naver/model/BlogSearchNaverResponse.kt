package com.kakao.api.domain.blog.naver.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class BlogSearchNaverResponse(
    val lastBuildDate: LocalDateTime, // 검색 결과를 생성한 시간
    val total: Int, // 총 검색 결과 개수
    @JsonProperty("start")
    val page: Int, // 검색 시작 위치
    val size: Int, // 한 번에 표시할 검색 결과 개수
    val items: List<BlogSearchNaverItemResponse>,
) {
    companion object {
        fun empty(): BlogSearchNaverResponse {
            return BlogSearchNaverResponse(
                lastBuildDate = LocalDateTime.now(),
                total = 0,
                page = 1,
                size = 0,
                items = emptyList()
            )
        }
    }
}