package ru.mironow.sentiment_analysis.web.response

import ru.mironow.sentiment_analysis.AnalysisClaimStage

/**
 * Analysis process business entity
 *
 * Created By Alexander Mironow - 23.02.2020
 */
data class AnalysisProcessRest(
        val stage: AnalysisClaimStage,
        val percent: Int
)