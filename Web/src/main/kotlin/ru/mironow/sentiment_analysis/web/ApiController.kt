package ru.mironow.sentiment_analysis.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.mironow.sentiment_analysis.business.analysys_claim.AnalysisClaimUseCase
import ru.mironow.sentiment_analysis.web.request.CreateClaimRequest
import ru.mironow.sentiment_analysis.web.response.AnalysisClaimRest
import ru.mironow.sentiment_analysis.web.response.Response

/**
 * Controller for work with service
 *
 * Created By Alex Mironow - 13.02.2020
 */
@RestController
class ApiController(val analysisClaimUseCase: AnalysisClaimUseCase) {
    /**
     * Запрос на анализ мнений пользователей по запросу [request]
     */
    @PostMapping("/create-analysis-claim")
    fun createAnalysisClaim(@RequestBody request: CreateClaimRequest): Response {
        val claim = analysisClaimUseCase.createAnalysisClaim(request.q)
        return Response( `object` = AnalysisClaimRest(claim) )
    }

    @GetMapping("/test")
    fun test() {
        analysisClaimUseCase.createAnalysisClaim("коронавирус")
    }
}