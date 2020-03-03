package ru.mironow.sentiment_analysis.business.analysys_claim

import org.springframework.scheduling.annotation.Async
import ru.mironow.sentiment_analysis.AnalysisClaim
import ru.mironow.sentiment_analysis.AnalysisProcess
import ru.mironow.sentiment_analysis.AnalysisResult

/**
 * Interface of component for analysis claim
 *
 * Created By Alexander Mironow - 19.02.2020
 */
interface AnalysisClaimUseCase {
    /**
     * Create analysis claim by [q]
     */
    fun createAnalysisClaim(q: String): AnalysisClaim

    /**
     * Execute sentiment analysis by [claim]
     */
    fun executeAnalysis(claim: AnalysisClaim)

    /**
     * Get analysis process by analysis claim [id]
     */
    fun analysisProcess(id: String): AnalysisProcess

    /**
     *  Result of sentiment analysis
     */
    fun analysisResult(claimId: String): AnalysisResult
}