package ru.mironow.sentiment_analysis.web.request

/**
 * Запрос на создание заявки
 * РЕСТ представление
 *
 * Created By Alexander Mironow - 13.02.2020
 */
data class CreateClaimRequest(
        val q: String
)