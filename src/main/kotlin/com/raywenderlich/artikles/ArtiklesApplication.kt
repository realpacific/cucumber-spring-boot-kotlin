package com.raywenderlich.artikles

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ArtiklesApplication

fun main(args: Array<String>) {
    runApplication<ArtiklesApplication>(*args)
}
