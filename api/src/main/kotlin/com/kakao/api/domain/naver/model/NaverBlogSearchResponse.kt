package com.kakao.api.domain.naver.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class NaverBlogSearchResponse(
    val lastBuildDate: String, // 검색 결과를 생성한 시간
    val total: Int, // 총 검색 결과 개수
    @JsonProperty("start")
    val page: Int, // 검색 시작 위치
    @JsonProperty("display")
    val size: Int, // 한 번에 표시할 검색 결과 개수
    val items: List<NaverBlogSearchItemResponse>,
) {
    companion object {
        fun empty(): NaverBlogSearchResponse {
            return NaverBlogSearchResponse(
                lastBuildDate = LocalDateTime.now().toString(),
                total = 0,
                page = 1,
                size = 0,
                items = emptyList()
            )
        }
    }
}