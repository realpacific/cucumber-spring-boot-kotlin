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

import java.lang.ThreadLocal
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import io.restassured.response.Response
import io.restassured.response.ValidatableResponse
import io.restassured.specification.RequestSpecification
import kotlin.concurrent.getOrSet

object StateHolder {

  private class State {
    var response: Response? = null
    var request: RequestSpecification? = null
    var payload: Any? = null

    /**
     * The value that uniquely identifies an entity
     */
    var differentiator: Any? = null
  }

  private val store: ThreadLocal<State> = ThreadLocal.withInitial { State() }

  private val state: State
    get() = store.get()

  fun setDifferentiator(value: Any) {
    state.differentiator = value
  }

  fun getDifferentiator(): Any? {
    return state.differentiator
  }

  fun setRequest(request: RequestSpecification) {
    state.request = request
  }

  fun getRequest(): RequestSpecification {
    var specs = state.request
    if (specs == null) {
      specs = given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
      setRequest(specs)
      return specs
    }
    return specs
  }

  fun setPayload(payload: Any): RequestSpecification {
    val specs = getRequest()
    state.payload = payload
    return specs.body(payload)
  }

  fun getPayloadOrNull(): Any? {
    return state.payload
  }

  fun getPayload(): Any {
    return getPayloadOrNull()!!
  }

  fun <T> getPayloadAs(klass: Class<T>): T {
    return klass.cast(getPayload())
  }

  fun getPayloadAsMap(): Map<*, *> {
    return getPayloadAs(Map::class.java)
  }

  fun setResponse(value: Response) {
    state.response = value
  }

  fun getValidatableResponse(): ValidatableResponse {
    return getResponse().then()
  }

  fun <T> extractPathValueFromResponse(path: String): T? {
    return extractPathValueFrom(path, getValidatableResponse())
  }

  private fun <T> extractPathValueFrom(path: String, response: ValidatableResponse): T? {
    return response.extract().body().path<T>(path)
  }

  fun getResponse() = getResponseOrNull()!!

  fun getResponseOrNull() = state.response

  fun clear() {
    store.remove()
  }
}