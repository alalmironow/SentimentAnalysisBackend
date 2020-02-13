package ru.mironow.sentiment_analysis.web

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

@SpringBootApplication
open class WebApplication : SpringBootServletInitializer() {
    override fun configure(builder: SpringApplicationBuilder?): SpringApplicationBuilder {
        return builder!!.sources(WebApplication::class.java)
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(WebApplication::class.java, *args)
}
