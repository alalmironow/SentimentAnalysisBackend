package ru.mironow.sentiment_analysis

import java.time.LocalDate

/**
 * Get statistic data in day
 *
 * Created By Alexander Mironow - 18.02.2020
 */
data class StatisticDay(
        val date: LocalDate,
        val countPositive: Int,
        val countNegative: Int,
        val count: Int,
        val statisticMessage: String? = null
)