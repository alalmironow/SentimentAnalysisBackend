package ru.mironow.sentiment_analysis.web.response

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * REST response
 *
 * Created By Alexander Mironow - 14.02.2020
 */
data class Response(
        @JsonProperty("error_code") val errorCode: Long = 0,
        @JsonProperty("error_message") val errorMessage: String = "",
        val `object`: Any?
)