package com.kakao.core.configuration


import com.fasterxml.jackson.databind.ObjectMapper
import com.kakao.core.configuration.properties.KakaoProperties
import com.kakao.core.configuration.properties.NaverProperties
import com.kakao.core.constant.KakaoConstants
import com.kakao.core.constant.NaverConstants
import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders.ACCEPT
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.function.client.ExchangeStrategies
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
    private val objectMapper: ObjectMapper,
) {

    fun defaultWebClient(): WebClient {
        val exchangeStrategies = ExchangeStrategies.builder()
            .codecs { configurer ->
                configurer.defaultCodecs().apply {
                    this.maxInMemorySize(1024 * 1024 * 50)
                }
            }.build()

        val httpClient = HttpClient.create(ConnectionProvider.newConnection())
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10 * 1000)
            .responseTimeout(Duration.ofSeconds(60))
            .doOnConnected {
                it.addHandlerLast(ReadTimeoutHandler(60, TimeUnit.SECONDS))
            }

        return WebClient.builder()
            .exchangeStrategies(exchangeStrategies)
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .build()
    }

    @Bean
    fun kakaoWebClient(): WebClient {
        return defaultWebClient().mutate()
            .baseUrl(KakaoConstants.BASE_URL)
            .defaultHeader(AUTHORIZATION, "KakaoAK ${kakaoProperties.restApiKey}")
            .build()
    }

    @Bean
    fun naverWebClient(): WebClient {
        return kakaoWebClient().mutate()
            .baseUrl(NaverConstants.BASE_URL)
            .defaultHeader(NaverConstants.X_NAVER_CLIENT_ID, naverProperties.clientId)
            .defaultHeader(NaverConstants.X_NAVER_CLIENT_SECRET, naverProperties.clientSecret)
            .build()
    }
}