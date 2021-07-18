package com.raywenderlich.artikles

import io.restassured.response.Response

object HttpUtils {

    private fun execute(block: () -> Response): Response? {
        val response = block()
        StateHolder.setResponse(response)
        return StateHolder.getResponse()
    }

    fun <T : Any> withPayload(payload: T, block: () -> Response?) {
        StateHolder.setPayload(payload)
        block()
    }

    fun executePost(url: String): Response? {
        return execute {
            StateHolder.getRequest().post(url)
        }
    }

    fun executePut(url: String): Response? {
        return execute {
            StateHolder.getRequest().put(url)
        }
    }

    fun executeGet(url: String): Response? {
        return execute {
            StateHolder.getRequest().get(url)
        }
    }

}