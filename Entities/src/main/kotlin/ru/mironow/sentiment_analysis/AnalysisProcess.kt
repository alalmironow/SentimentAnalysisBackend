package ru.mironow.sentiment_analysis

/**
 * Execute analysis claim data
 *
 * Created By Alexander Mironow - 23.02.2020
 */
data class AnalysisProcess(
        val stage: AnalysisClaimStage,
        val percent: Int
)