/*
 * Copyright (c) 2020 Razeware LLC
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

object StateHolder {

  enum class Type {
    RESPONSE,
    REQUEST,
    PAYLOAD,

    /**
     * Holds value that uniquely differentiates an entity
     */
    DIFFERENTIATOR_FIELD,
  }

  private val store: ThreadLocal<MutableMap<Type, Any>> = ThreadLocal.withInitial { mutableMapOf() }

  private val state
    get() = store.get()

  fun setDifferentiator(value: Any) {
    state[Type.DIFFERENTIATOR_FIELD] = value
  }

  fun getDifferentiator(): Any? {
    return state[Type.DIFFERENTIATOR_FIELD]
  }

  fun setRequest(request: RequestSpecification) {
    state[Type.REQUEST] = request
  }

  fun getRequest(): RequestSpecification {
    var specs = state[Type.REQUEST] as RequestSpecification?
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
    state[Type.PAYLOAD] = payload
    return specs.body(payload)
  }

  fun getPayloadOrNull(): Any? {
    return state[Type.PAYLOAD]
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
    state[Type.RESPONSE] = value
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
  fun getResponseOrNull() = state[Type.RESPONSE] as Response?

  operator fun get(key: Type): Any? {
    return state[key]
  }

  fun clear() {
    store.remove()
  }

  private fun set(field: Type, value: Any) {
    state[field] = value
  }

  /**
   * Backups [fields] and then clears [state] and restores values in [fields]
   */
  fun clearWithCopy(vararg fields: Type) {
    if (fields.isEmpty()) {
      clear()
      return
    }
    val temp = mutableMapOf<Type, Any>()
    fields.forEach {
      val value = state[it]
      if (value != null) {
        temp[it] = value
      }
    }
    clear()
    temp.forEach { (key, value) ->
      set(key, value)
    }
  }
}