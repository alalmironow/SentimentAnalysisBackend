package ru.mironow.sentiment_analysis.data_service

import ru.mironow.sentiment_analysis.SocialData

/**
 *  Interface for social network service
 *  For getting messages from internet
 */
interface SocialNetworkService {
    /**
     * Get data from social network
     * by [q]
     */
    fun getData(q: String): List<SocialData>
}