package com.raywenderlich.artikles.tests

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    monochrome = false,
    plugin = [
        "pretty", "summary", "html:reports/articles/create-update-articles.html",
    ],
    features = ["classpath:features/articles/create"],
)
class CreateArticleTest