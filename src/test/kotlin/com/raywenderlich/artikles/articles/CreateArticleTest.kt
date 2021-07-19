package com.raywenderlich.artikles.articles

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    monochrome = false,
    plugin = [
        "pretty", "summary", "html:reports/articles/create-articles.html",
    ],
    features = ["classpath:features/create"],
)
class CreateArticleTest