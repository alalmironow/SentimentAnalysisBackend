package ru.mironow.sentiment_analysis

import com.mongodb.MongoClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

/**
 * Config MongoDB
 *
 * Created By Alexander Mironow - 14.02.2020
 */
@Configuration
@PropertySource(value = ["classpath:application-mongo.properties"], ignoreResourceNotFound = false)
open class MongoDBConfig {
    @Value("\${mongo.host}") lateinit var host: String
    @Value("\${mongo.port}") var port: Int = 0
    @Value("\${mongo.database}") lateinit var database: String

    @Bean
    open fun database() = MongoClient(host, port).getDB(database)
}