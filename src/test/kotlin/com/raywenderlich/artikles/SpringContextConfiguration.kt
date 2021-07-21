package com.raywenderlich.artikles

import org.springframework.boot.test.context.SpringBootTest
import io.cucumber.spring.CucumberContextConfiguration

@SpringBootTest(
    classes = [ArtiklesApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@CucumberContextConfiguration
class SpringContextConfiguration