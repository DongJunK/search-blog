package com.kakao.api.application.service.model

import com.kakao.api.domain.kakao.model.KakaoBlogSearchResponse
import com.kakao.api.domain.naver.model.NaverBlogSearchResponse
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class BlogSearchResponse(
    val totalCount: Int,
    val contents: List<BlogSearchDocumentResponse>,
) {
    companion object {
        fun createBy(
            response: KakaoBlogSearchResponse,
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

        fun createBy(response: NaverBlogSearchResponse): BlogSearchResponse {
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

        fun empty(): BlogSearchResponse {
            return BlogSearchResponse(
                totalCount = 0,
                contents = emptyList()
            )
        }
    }
}
