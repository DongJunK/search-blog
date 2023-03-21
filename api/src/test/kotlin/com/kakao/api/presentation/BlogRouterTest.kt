package com.kakao.api.presentation

import com.kakao.api.application.service.BlogSearchService
import com.kakao.api.application.service.model.BlogSearchResponse
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.ApplicationContext
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@WebFluxTest
@ActiveProfiles("test")
class BlogRouterTest(
    private val context: ApplicationContext,
) {
    @MockkBean
    private lateinit var blogSearchService: BlogSearchService

    private lateinit var webTestClient: WebTestClient

    @BeforeEach
    fun setup() {
        webTestClient = WebTestClient.bindToApplicationContext(context).build()
    }

    @Test
    fun searchBlog() {
        coEvery { blogSearchService.searchBlog(any()) } returns BlogSearchResponse.empty()

        webTestClient.get()
            .uri("/v1/blog/search?keyword=test&page=5&size=100&sortType=RECENCY&blogUrl=https://www.test.com")
            .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
            .exchange()
            .expectStatus().isOk
            .expectBody(BlogSearchResponse::class.java)
            .value {
                assertEquals(it, BlogSearchResponse.empty())
            }
    }
}