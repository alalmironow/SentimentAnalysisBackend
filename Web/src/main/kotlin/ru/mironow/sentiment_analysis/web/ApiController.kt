package ru.mironow.sentiment_analysis.web

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.mironow.sentiment_analysis.web.request.CreateClaimRequest
import ru.mironow.sentiment_analysis.web.response.ResultClaimRest

/**
 * Контроллер для работы с сервисом
 *
 * Created By Alex Mironow - 13.02.2020
 */
@RestController
class ApiController {
    data class Response(
            @JsonProperty("error_code") val errorCode: Long = 0,
            @JsonProperty("error_message") val errorMessage: String = "",
            val `object`: Any
    )

    /**
     * Запрос на анализ мнений пользователей по запросу [request]
     */
    @PostMapping("/create-analysis-claim")
    fun createAnalysisClaim(@RequestBody request: CreateClaimRequest) = Response(`object` = ResultClaimRest(123L))
}