package com.kakao.api.application.service.model

import com.kakao.api.domain.popularKeyword.service.model.PopularKeywordDomainResponse

data class PopularKeywordDocumentResponse(
    val keyword: String,
    val searchCount: Int,
) {
    companion object {
        fun createBy(response: PopularKeywordDomainResponse): PopularKeywordDocumentResponse {
            return PopularKeywordDocumentResponse(
                keyword = response.keyword,
                searchCount = response.searchCount
            )
        }
    }
}