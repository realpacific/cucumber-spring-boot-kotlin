package com.raywenderlich.artikles.steps

import com.raywenderlich.artikles.HttpUtils
import com.raywenderlich.artikles.SpringContextConfiguration
import com.raywenderlich.artikles.StateHolder
import io.cucumber.java.BeforeStep
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.cucumber.spring.CucumberContextConfiguration
import io.restassured.RestAssured
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.hamcrest.Matchers.equalTo
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus

@CucumberContextConfiguration
class ArticleStepDefs : SpringContextConfiguration() {
    @LocalServerPort
    val port: Int? = 0

    @BeforeStep
    fun setup() {
        RestAssured.baseURI = "http://localhost:$port"
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
    }

    @Given("Create an article with following fields")
    fun createAnArticleWithFollowingFields(payload: Map<String, String>) {
        HttpUtils.withPayload(payload) {
            HttpUtils.executePost("/articles")
        }
        StateHolder.getValidatableResponse().statusCode(HttpStatus.OK.value())
        StateHolder.setDifferentiator(StateHolder.extractPathValueFromResponse<String>("id")!!)
    }

    @Given("Update article with following fields")
    fun updateArticleWithFollowingFields(payload: Map<String, String>) {
        HttpUtils.withPayload(payload) {
            HttpUtils.executePut("/articles/${StateHolder.getDifferentiator()}")
        }
        StateHolder.getValidatableResponse().statusCode(HttpStatus.OK.value())
        StateHolder.setDifferentiator(StateHolder.extractPathValueFromResponse<String>("id")!!)
    }

    @Then("{string} should not be null")
    fun shouldNotBeNull(path: String) {
        StateHolder.getValidatableResponse().body(path, Matchers.notNullValue())
    }

    @Then("{string} should be equal to {string}")
    fun shouldBeEqual(path: String, right: Comparable<Any>) {
        StateHolder.getValidatableResponse().body(path, equalTo(right))
    }

    @Then("{string} should be equal to differentiator")
    fun shouldBeEqualToDifferentiator(path: String) {
        StateHolder.getValidatableResponse().body(path, equalTo(StateHolder.getDifferentiator()))
    }

    @Then("{string} should be same as that in payload")
    fun pathValueShouldBeSameAsPayload(path: String) {
        val valueFromResponse = StateHolder.getValidatableResponse().extract().body().path<Comparable<Any>>(path)
        val valueFromPayload = StateHolder.getPayloadAsMap()[path]
        assert(valueFromResponse.equals(valueFromPayload))
    }

    @When("Fetch article by id")
    fun fetchArticleById() {
        val id = StateHolder.getDifferentiator()
        StateHolder.clearWithCopy(StateHolder.Type.DIFFERENTIATOR_FIELD)
        HttpUtils.executeGet("/articles/${id}")!!
    }

    @When("Fetch article using id of {string}")
    fun fetchArticleUsingId(id: String) {
        HttpUtils.executeGet("/articles/${id}")!!
    }

    @Then("Should succeed")
    fun requestShouldSucceed() {
        assertThat(
            StateHolder.getResponse()!!.statusCode,
            Matchers.allOf(
                Matchers.greaterThanOrEqualTo(200),
                Matchers.lessThan(300)
            )
        )
    }

    @Then("Should have status of {int}")
    fun requestShouldSucceed(statsCode: Int) {
        assertThat(
            StateHolder.getResponse()!!.statusCode,
            equalTo(statsCode)
        )
    }
}