package com.raywenderlich.artikles.configurations

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JacksonConfig {

    @Bean
    fun providesJackson(): ObjectMapper = ObjectMapper()
        .registerModule(KotlinModule())
        .registerModule(JavaTimeModule()).also {
            it.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        }

}