package com.raywenderlich.artikles

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.lang.RuntimeException

class NotFoundException(entityName: String) : RuntimeException("$entityName not found.")

data class ErrorResponse(val message: String) {

    constructor(exception: Exception) : this(exception.message ?: "") {

    }
}

@ControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(exception: NotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(ErrorResponse(exception), HttpStatus.NOT_FOUND)
    }


}