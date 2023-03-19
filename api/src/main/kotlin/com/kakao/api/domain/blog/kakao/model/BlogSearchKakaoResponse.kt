package com.kakao.api.domain.blog.kakao.model

data class BlogSearchKakaoResponse(
    val meta: BlogSearchKakaoMetaResponse,
    val documents: List<BlogSearchKakaoDocumentResponse>,
) {

    companion object {
        fun empty(): BlogSearchKakaoResponse {
            return BlogSearchKakaoResponse(
                meta = BlogSearchKakaoMetaResponse(
                    totalCount = 0,
                    pageableCount = 0,
                    isEnd = true
                ),
                documents = emptyList()
            )
        }
    }
}