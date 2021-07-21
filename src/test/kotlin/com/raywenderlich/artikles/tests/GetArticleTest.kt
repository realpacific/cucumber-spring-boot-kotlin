package com.raywenderlich.artikles.tests

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    monochrome = false,
    plugin = [
        "pretty", "summary", "html:reports/articles/get-articles.html",
    ],
    features = ["classpath:features/articles/get"],
)
class GetArticleTest