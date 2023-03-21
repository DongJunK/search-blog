package com.kakao.api.application.service.model

import com.kakao.api.domain.popularKeyword.service.model.PopularKeywordDomainResponse

data class PopularKeywordResponse(
    val totalCount: Int,
    val popularKeywords: List<PopularKeywordDocumentResponse>,
) {
    companion object {
        fun createBy(responses: List<PopularKeywordDomainResponse>): PopularKeywordResponse {
            return PopularKeywordResponse(
                totalCount = responses.size,
                popularKeywords = responses.map { PopularKeywordDocumentResponse.createBy(it) }
            )
        }
    }
}