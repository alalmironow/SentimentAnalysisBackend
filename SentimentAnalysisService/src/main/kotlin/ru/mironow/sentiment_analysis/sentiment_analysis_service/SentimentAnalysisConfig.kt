package ru.mironow.sentiment_analysis.sentiment_analysis_service

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import sun.net.www.http.HttpClient

/**
 * Config for analysis sentiment service
 *
 * Created By Alexander Mironow - 18.02.2020
 */
@Configuration
@PropertySource(value = ["classpath:application-sentiment.properties"], ignoreResourceNotFound = true)
open class SentimentAnalysisConfig {
    @Value("\${sentiment_analysis.rest.api.host}") lateinit var baseUrl: String

    @Bean
    open fun sentimentApiService(): RestApi {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://$baseUrl")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(RestApi::class.java)
    }
}


interface RestApi {
    /**
     * Analysis sentiment text
     */
    @POST("/api/analysis-sentiment")
    fun analysisSentiment(@Body request: AnalysisRequest): Call<String>
}