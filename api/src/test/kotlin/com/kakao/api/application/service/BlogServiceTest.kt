package com.kakao.api.application.service

import com.kakao.api.application.service.model.BlogSearchRequest
import com.kakao.api.application.service.model.BlogSearchResponse
import com.kakao.api.domain.kakao.client.KakaoClient
import com.kakao.api.domain.kakao.model.KakaoBlogSearchMetaResponse
import com.kakao.api.domain.kakao.model.KakaoBlogSearchResponse
import com.kakao.api.domain.naver.client.NaverClient
import com.kakao.api.domain.naver.model.NaverBlogSearchResponse
import com.kakao.api.domain.popularKeyword.service.PopularKeywordDomainService
import com.kakao.core.error.errorcode.ClientErrorCode
import com.kakao.core.error.errorcode.ServerErrorCode
import com.kakao.core.error.exception.ClientException
import com.kakao.core.error.exception.KakaoServerException
import com.kakao.core.error.exception.NaverServerException
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
class BlogServiceTest {
    @MockK(relaxed = true)
    private lateinit var kakaoClient: KakaoClient

    @MockK(relaxed = true)
    private lateinit var naverClient: NaverClient

    @MockK(relaxed = true)
    private lateinit var popularKeywordDomainService: PopularKeywordDomainService

    private lateinit var blogService: BlogService

    @BeforeEach
    fun setup() {
        blogService = BlogService(
            kakaoClient = kakaoClient,
            naverClient = naverClient,
            popularKeywordDomainService = popularKeywordDomainService
        )

        every { popularKeywordDomainService.increaseSearchCount(any()) } just runs
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
            blogService.searchBlog(blogSearchRequest)
        }
        assertEquals(BlogSearchResponse.empty(), response)
    }

    @Test
    fun `searchBlog(카카오 4xx 에러 케이스)`() {
        val blogSearchRequest = BlogSearchRequest(keyword = "test")

        coEvery { kakaoClient.searchBlog(any()) } throws ClientException(ClientErrorCode.REQUEST_ERROR)

        assertThrows<ClientException> {
            runBlocking {
                blogService.searchBlog(blogSearchRequest)
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
            blogService.searchBlog(blogSearchRequest)
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
                blogService.searchBlog(blogSearchRequest)
            }
        }
    }
}