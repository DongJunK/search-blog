package com.kakao.api.domain.popularKeyword.service.model

import com.kakao.api.domain.popularKeyword.entity.PopularKeyword

data class PopularKeywordDomainResponse(
    val keyword: String,
    val searchCount: Int,
) {
    companion object {
        fun createBy(popularKeyword: PopularKeyword): PopularKeywordDomainResponse {
            return PopularKeywordDomainResponse(
                keyword = popularKeyword.keyword,
                searchCount = popularKeyword.searchCount
            )
        }
    }
}