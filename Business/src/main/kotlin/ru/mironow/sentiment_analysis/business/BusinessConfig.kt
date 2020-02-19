package ru.mironow.sentiment_analysis.business

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

/**
 * Config for business module
 *
 * Created By Alexander Mironow - 19.02.2020
 */
@EnableAsync
@Configuration
open class BusinessConfig {
    @Bean
    open fun threadPoolTaskExecutor(): Executor = ThreadPoolTaskExecutor()
}