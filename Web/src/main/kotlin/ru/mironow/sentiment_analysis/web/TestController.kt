package ru.mironow.sentiment_analysis.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    data class Response(val l: Long)

    @GetMapping("/test")
    fun test() = Response(2020L)
}