package com.raywenderlich.artikles

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    monochrome = false,
    plugin = ["pretty", "html:target/cucumber", "summary"],
    features = ["classpath:features/create-article.feature"],
)
class RunCucumberTest