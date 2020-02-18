package ru.mironow.sentiment_analysis

/**
 * Get statistic data in day
 *
 * Created By Alexander Mironow - 18.02.2020
 */
data class StatisticDay(
    val countPositive: Int,
    val countNegative: Int,
    val count: Int,
    val statisticMessage: String? = null
)