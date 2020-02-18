package ru.mironow.sentiment_analysis

import java.time.LocalDate

/**
 * Social data in month
 *
 * Created By Alexander Mironow - 18.02.2020
 */
data class MonthSocialData(
        val date: LocalDate,
        val data: List<SocialData>
)