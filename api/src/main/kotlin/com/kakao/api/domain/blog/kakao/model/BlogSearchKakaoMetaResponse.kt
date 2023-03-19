package com.kakao.api.domain.blog.kakao.model

import com.fasterxml.jackson.annotation.JsonProperty

data class BlogSearchKakaoMetaResponse(
    @JsonProperty("total_count")
    val totalCount: Int, // 검색된 문서 수
    @JsonProperty("pageable_count")
    val pageableCount: Int, // total_count 중 노출 가능 문서 수
    @JsonProperty("is_end")
    val isEnd: Boolean, // 현재 페이지가 마지막 페이지인지 여부
)
