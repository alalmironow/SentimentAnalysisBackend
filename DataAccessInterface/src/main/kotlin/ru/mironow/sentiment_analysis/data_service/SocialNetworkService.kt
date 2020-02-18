package ru.mironow.sentiment_analysis.data_service

import ru.mironow.sentiment_analysis.MonthSocialData
import ru.mironow.sentiment_analysis.SocialData

/**
 *  Interface for social network service
 *  For getting messages from internet
 */
interface SocialNetworkService {
    /**
     * Get relevant data from social network
     * by [q]
     */
    fun getRelevantData(q: String): List<SocialData>

    /**
     * Get year data from social network
     * by [q]
     */
    fun getYearData(q: String): List<MonthSocialData>
}