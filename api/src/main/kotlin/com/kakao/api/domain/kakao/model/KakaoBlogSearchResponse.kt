package com.kakao.api.domain.kakao.model

data class KakaoBlogSearchResponse(
    val meta: KakaoBlogSearchMetaResponse,
    val documents: List<KakaoBlogSearchDocumentResponse>,
) {

    companion object {
        fun empty(): KakaoBlogSearchResponse {
            return KakaoBlogSearchResponse(
                meta = KakaoBlogSearchMetaResponse(
                    totalCount = 0,
                    pageableCount = 0,
                    isEnd = true
                ),
                documents = emptyList()
            )
        }
    }
}