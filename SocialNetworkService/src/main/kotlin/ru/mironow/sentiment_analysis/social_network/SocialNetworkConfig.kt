package ru.mironow.sentiment_analysis.social_network

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource(value = ["social.properties"])
open class SocialNetworkConfig