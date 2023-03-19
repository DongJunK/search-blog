package com.kakao.core.configuration


import com.kakao.core.configuration.properties.KakaoProperties
import com.kakao.core.configuration.properties.NaverProperties
import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import java.time.Duration
import java.util.concurrent.TimeUnit

@Configuration
@ConfigurationPropertiesScan
class WebClientConfig(
    private val kakaoProperties: KakaoProperties,
    private val naverProperties: NaverProperties,
) {

    fun defaultWebClient(): WebClient{
        val httpClient = HttpClient.create(ConnectionProvider.newConnection())
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10 * 1000)
            .responseTimeout(Duration.ofSeconds(60))
            .doOnConnected {
                it.addHandlerLast(ReadTimeoutHandler(60, TimeUnit.SECONDS))
            }

        return WebClient.builder()
            .codecs { it.defaultCodecs().maxInMemorySize(1024 * 1024 * 50) }
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .build()
    }
    @Bean
    fun kakaoWebClient(): WebClient {
        return defaultWebClient().mutate()
            .defaultHeader(AUTHORIZATION, "KakaoAK ${kakaoProperties.restApiKey}")
            .build()
    }

    @Bean
    fun naverWebClient(): WebClient {
        return kakaoWebClient().mutate()
            .defaultHeader(X_NAVER_CLIENT_ID, naverProperties.clientId)
            .defaultHeader(X_NAVER_CLIENT_SECRET, naverProperties.clientSecret)
            .build()
    }
    companion object{
        const val X_NAVER_CLIENT_ID = "X-Naver-Client-Id"
        const val X_NAVER_CLIENT_SECRET = "X-Naver-Client-Secret"
    }
}