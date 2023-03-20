package com.kakao.api.application.service

import com.kakao.api.application.service.model.BlogSearchRequest
import com.kakao.api.application.service.model.BlogSearchResponse
import com.kakao.api.domain.blog.kakao.client.KakaoClient
import com.kakao.api.domain.blog.kakao.model.KakaoBlogSearchMetaResponse
import com.kakao.api.domain.blog.kakao.model.KakaoBlogSearchResponse
import com.kakao.api.domain.blog.naver.client.NaverClient
import com.kakao.api.domain.blog.naver.model.NaverBlogSearchResponse
import com.kakao.core.error.errorcode.ClientErrorCode
import com.kakao.core.error.errorcode.ServerErrorCode
import com.kakao.core.error.exception.ClientException
import com.kakao.core.error.exception.KakaoServerException
import com.kakao.core.error.exception.NaverServerException
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
class BlogSearchServiceTest {
    @MockK(relaxed = true)
    private lateinit var kakaoClient: KakaoClient

    @MockK(relaxed = true)
    private lateinit var naverClient: NaverClient

    private lateinit var blogSearchService: BlogSearchService

    @BeforeEach
    fun setup() {
        blogSearchService = BlogSearchService(
            kakaoClient = kakaoClient,
            naverClient = naverClient
        )
    }

    @Test
    fun `searchBlog(정상 케이스)`() {
        val blogSearchRequest = BlogSearchRequest(keyword = "test")
        val kakaoBlogSearchResponse = KakaoBlogSearchResponse(
            meta = KakaoBlogSearchMetaResponse(
                totalCount = 0,
                pageableCount = 0,
                isEnd = true
            ),
            documents = emptyList()
        )
        coEvery { kakaoClient.searchBlog(any()) } returns kakaoBlogSearchResponse

        val response = runBlocking {
            blogSearchService.searchBlog(blogSearchRequest)
        }
        assertEquals(BlogSearchResponse.empty(), response)
    }

    @Test
    fun `searchBlog(카카오 4xx 에러 케이스)`() {
        val blogSearchRequest = BlogSearchRequest(keyword = "test")

        coEvery { kakaoClient.searchBlog(any()) } throws ClientException(ClientErrorCode.REQUEST_ERROR)

        assertThrows<ClientException> {
            runBlocking {
                blogSearchService.searchBlog(blogSearchRequest)
            }
        }
    }

    @Test
    fun `searchBlog(카카오 5xx 에러 케이스)`() {
        val blogSearchRequest = BlogSearchRequest(keyword = "test")
        val naverBlogSearchResponse = NaverBlogSearchResponse(
            lastBuildDate = LocalDate.now().toString(),
            total = 0,
            page = 1,
            size = 10,
            items = emptyList(),
        )

        coEvery { kakaoClient.searchBlog(any()) } throws KakaoServerException(ServerErrorCode.INTERNAL_SERVER_ERROR)
        coEvery { naverClient.searchBlog(any()) } returns naverBlogSearchResponse

        val response = runBlocking {
            blogSearchService.searchBlog(blogSearchRequest)
        }

        assertEquals(BlogSearchResponse.empty(), response)
    }

    @Test
    fun `searchBlog(카카오 5xx 에러, 네이버 5xx 케이스)`() {
        val blogSearchRequest = BlogSearchRequest(keyword = "test")

        coEvery { kakaoClient.searchBlog(any()) } throws KakaoServerException(ServerErrorCode.INTERNAL_SERVER_ERROR)
        coEvery { naverClient.searchBlog(any()) } throws NaverServerException(ServerErrorCode.INTERNAL_SERVER_ERROR)

        assertThrows<NaverServerException> {
            runBlocking {
                blogSearchService.searchBlog(blogSearchRequest)
            }
        }
    }
}