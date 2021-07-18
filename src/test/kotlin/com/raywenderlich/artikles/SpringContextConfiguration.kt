package com.raywenderlich.artikles

import org.springframework.boot.test.context.SpringBootTest
import io.cucumber.spring.CucumberContextConfiguration

@SpringBootTest(
    classes = [ArtiklesApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = ["spring.jpa.hibernate.ddl-auto=create-drop"]
)
@CucumberContextConfiguration
class SpringContextConfiguration() {
}