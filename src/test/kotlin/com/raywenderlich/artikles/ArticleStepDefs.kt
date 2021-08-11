/*
 * Copyright (c) 2021 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.artikles

import com.raywenderlich.artikles.repositories.ArticleRepository
import io.cucumber.java.Before
import io.cucumber.java.BeforeStep
import io.cucumber.java.Scenario
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.restassured.RestAssured
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.server.LocalServerPort

class ArticleStepDefs : SpringContextConfiguration() {
  @LocalServerPort
  private var port: Int? = 0

  @Autowired
  private lateinit var _repository: ArticleRepository

  @Before
  fun setup(scenario: Scenario) {
    println("## Scenario: ${scenario.name} Running on: ${Thread.currentThread().name}")
    // Required when running in non-parallel mode
    StateHolder.clear()
    RestAssured.baseURI = "http://localhost:$port"
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
  }

  @BeforeStep
  fun step(scenario: Scenario) {
    println("## Scenario: ${scenario.name} Steps running on: ${Thread.currentThread().name}")
  }

  @Before("@requiresDBClear")
  fun requiresDBClear(scenario: Scenario) {
    println("Clearing table for ${scenario.name}")
    _repository.deleteAll()
  }

  @Given("Create an article with following fields")
  fun createAnArticleWithFollowingFields(payload: Map<String, Any>) {
    withPayload(payload) {
      HttpUtils.executePost("/${Resources.ARTICLES}")
    }
    if (StateHolder.getResponse().statusCode == 200) {
      StateHolder.setDifferentiator(StateHolder.extractPathValueFromResponse<String>("id")!!)
    }
  }

  @Given("Bulk create articles with following fields")
  fun bulkCreateArticles(payloads: List<Map<String, Any>>) {
    payloads.forEach {
      createAnArticleWithFollowingFields(it)
    }
  }

  @Given("Update article with following fields")
  fun updateArticleWithFollowingFields(payload: Map<String, Any>) {
    withPayload(payload) {
      HttpUtils.executePut("/${Resources.ARTICLES}/${StateHolder.getDifferentiator()}")
    }
    if (StateHolder.getResponse().statusCode == 200) {
      StateHolder.setDifferentiator(StateHolder.extractPathValueFromResponse<String>("id")!!)
    }
  }

  @Then("{string} should not be null")
  fun shouldNotBeNull(path: String) {
    StateHolder.getValidatableResponse().body(path, notNullValue())
  }

  @Then("{string} should be equal to {string}")
  fun shouldBeEqual(path: String, right: String) {
    StateHolder.getValidatableResponse().body(path, equalTo(right))
  }

  @Then("{string} should include {string}")
  fun shouldInclude(path: String, content: String) {
    val list = StateHolder.getValidatableResponse().extract().body().path<List<String>>(path)
    assertThat(list, containsInAnyOrder(content))
  }

  @Then("{string} should be equal to differentiator")
  fun shouldBeEqualToDifferentiator(path: String) {
    StateHolder.getValidatableResponse().body(path, equalTo(StateHolder.getDifferentiator()))
  }

  @Then("{string} should be same as that in payload")
  fun pathValueShouldBeSameAsPayload(path: String) {
    val valueFromResponse =
      StateHolder.getValidatableResponse().extract().body().path<Comparable<Any>>(path)
    val valueFromPayload = StateHolder.getPayloadAsMap()[path]
    assert(valueFromResponse.equals(valueFromPayload))
  }

  @When("Fetch article by id")
  fun fetchArticleById() {
    val id = StateHolder.getDifferentiator()
    requireNotNull(id)
    HttpUtils.executeGet("/${Resources.ARTICLES}/${id}")
  }

  @When("Fetch article using id of {string}")
  fun fetchArticleUsingId(id: String) {
    HttpUtils.executeGet("/${Resources.ARTICLES}/${id}")
  }

  @When("Fetch all articles")
  fun fetchAllArticles() {
    HttpUtils.executeGet(Resources.ARTICLES)
  }

  @When("Delete article")
  fun deleteArticle() {
    val id = StateHolder.getDifferentiator()
    HttpUtils.executeDelete("${Resources.ARTICLES}/${id}")
  }


  @Then("Should succeed")
  fun requestShouldSucceed() {
    assertThat(
      StateHolder.getResponse().statusCode,
      allOf(
        greaterThanOrEqualTo(200),
        lessThan(300)
      )
    )
  }

  @Then("Should have status of {int}")
  fun requestShouldHaveStatusCodeOf(statusCode: Int) {
    assertThat(
      StateHolder.getResponse().statusCode,
      equalTo(statusCode)
    )
  }

  @Then("Should have size of {int}")
  fun shouldHaveSizeOf(size: Int) {
    assertThat(
      StateHolder.getValidatableResponse().extract().body().path<List<Any>>(""),
      hasSize(size)
    )
  }
}