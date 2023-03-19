package com.kakao.api.presentation.router

import com.kakao.api.presentation.handler.BlogSearchHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class BlogSearchRouter(
    private val blogSearchHandler: BlogSearchHandler,
) {
    @Bean
    fun searchBlogRouter(): RouterFunction<ServerResponse> = coRouter {
        (accept(MediaType.APPLICATION_JSON) and "/v1").nest {
            "/blog".nest {
                GET("/search", blogSearchHandler::searchBlog)
            }
        }
    }
}