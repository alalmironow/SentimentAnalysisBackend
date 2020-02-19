package ru.mironow.sentiment_analysis

import java.time.LocalDate

/**
 * Social data in date
 *
 * Created By Alexander Mironow - 18.02.2020
 */
data class DateSocialData(
        val date: LocalDate,
        val data: List<SocialData>
)