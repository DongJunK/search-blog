package com.kakao.api.application.service.model

import java.time.LocalDate

data class BlogSearchDocumentResponse(
    val title: String,
    val blogName: String,
    val blogPostUrl: String,
    val description: String,
    val postDate: String,
)