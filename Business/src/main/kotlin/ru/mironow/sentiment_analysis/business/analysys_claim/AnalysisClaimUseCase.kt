package ru.mironow.sentiment_analysis.business.analysys_claim

import ru.mironow.sentiment_analysis.AnalysisClaim

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
}