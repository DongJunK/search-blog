package com.kakao.api.application.service.model

import com.kakao.api.domain.blog.kakao.model.BlogSearchKakaoResponse
import com.kakao.api.domain.blog.naver.model.BlogSearchNaverResponse
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class BlogSearchResponse(
    val totalCount: Int,
    val contents: List<BlogSearchDocumentResponse>,
) {
    companion object {
        fun createBy(
            response: BlogSearchKakaoResponse,
        ): BlogSearchResponse {
            return BlogSearchResponse(
                totalCount = response.meta.totalCount,
                contents = response.documents.map {
                    BlogSearchDocumentResponse(
                        title = it.title,
                        blogName = it.blogName,
                        blogPostUrl = it.blogPostUrl,
                        description = it.contents,
                        postDate = LocalDate.parse(
                            it.datetime,
                            DateTimeFormatter.ISO_OFFSET_DATE_TIME
                        ).toString()
                    )
                }
            )
        }

        fun createBy(response: BlogSearchNaverResponse): BlogSearchResponse {
            return BlogSearchResponse(
                totalCount = response.total,
                contents = response.items.map {
                    BlogSearchDocumentResponse(
                        title = it.title,
                        blogName = it.bloggerName,
                        blogPostUrl = it.blogPostUrl,
                        description = it.description,
                        postDate = LocalDate.parse(
                            it.postDate,
                            DateTimeFormatter.ofPattern("yyyyMMdd")
                        ).toString()
                    )
                }
            )
        }
    }
}
