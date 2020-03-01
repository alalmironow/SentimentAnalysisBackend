package ru.mironow.sentiment_analysis.web

import org.springframework.web.bind.annotation.*
import ru.mironow.sentiment_analysis.business.analysys_claim.AnalysisClaimUseCase
import ru.mironow.sentiment_analysis.web.request.CreateClaimRequest
import ru.mironow.sentiment_analysis.web.response.AnalysisProcessRest
import ru.mironow.sentiment_analysis.web.response.AnalysisResultRest
import ru.mironow.sentiment_analysis.web.response.Response

/**
 * Controller for work with service
 *
 * Created By Alex Mironow - 13.02.2020
 */
@RestController
class ApiController(val analysisClaimUseCase: AnalysisClaimUseCase) {
    /**
     * Request for sentiment analysis by text string [request]
     */
    @PostMapping("/api/create-analysis-claim")
    fun createAnalysisClaim(@RequestBody request: CreateClaimRequest): Response {
        val claim = analysisClaimUseCase.createAnalysisClaim(request.q)
        return Response( `object` = claim.id )
    }

    /**
     * Request for analysis process by [id]
     */
    @GetMapping("/api/analysis-process")
    fun analysisProcess(@RequestParam id: String): Response {
        val process = analysisClaimUseCase.analysisProcess(id)
        val restProcess = AnalysisProcessRest(
                stage = process.stage,
                percent = process.percent
        )
        return Response(`object` = restProcess)
    }

    /**
     * Request for analysis result by [key]
     */
    @GetMapping("/api/analysis-result")
    fun analysisResult(@RequestParam("claim_id") claimId: String): Response {
        val result = analysisClaimUseCase.analysisResult(claimId)
        val restResult = AnalysisResultRest(result)
        return Response(`object` = restResult)
    }

    @GetMapping("/test")
    fun test() {
        analysisClaimUseCase.createAnalysisClaim("Футбол сборная россии")
    }
}