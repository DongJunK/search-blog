package com.kakao.api.presentation

import com.kakao.api.application.service.BlogService
import com.kakao.api.application.service.model.BlogSearchResponse
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.ApplicationContext
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.web.reactive.server.WebTestClient

@WebFluxTest
@ConfigurationPropertiesScan(basePackages = ["com.kakao"])
class BlogRouterTest(
    private val context: ApplicationContext,
) {
    @MockK(relaxed = true)
    private lateinit var blogService: BlogService

    private lateinit var webTestClient: WebTestClient

    @BeforeEach
    fun setup() {
        webTestClient = WebTestClient.bindToApplicationContext(context).build()
    }

    @Test
    fun searchBlog() {
        coEvery { blogService.searchBlog(any()) } returns BlogSearchResponse.empty()

        webTestClient.get()
            .uri("/v1/blog/search")
            .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
            .exchange()
            .expectStatus().isOk
            .expectBody(BlogSearchResponse::class.java)
            .value {
                assertEquals(it, BlogSearchResponse.empty())
            }
    }
}