package com.raywenderlich.artikles

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.validation.ConstraintViolationException

class NotFoundException(entityName: String) : RuntimeException("$entityName not found.")

data class ErrorResponse(val errors: List<String>)

@ControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(exception: NotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(ErrorResponse(listOfNotNull(exception.message)), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolation(exception: ConstraintViolationException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(exception.constraintViolations.map { it.message }),
            HttpStatus.BAD_REQUEST
        )
    }
}