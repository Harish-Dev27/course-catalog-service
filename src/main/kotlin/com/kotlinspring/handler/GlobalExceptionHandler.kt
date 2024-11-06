package com.kotlinspring.handler

import com.kotlinspring.exception.InstructorIdNotPresentException
import mu.KLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@Component
@ControllerAdvice
class GlobalExceptionHandler: ResponseEntityExceptionHandler() {

    companion object: KLogging()

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        logger.error("Method Argument exception error message: ${ex.message}, $ex")

        val errors = ex.bindingResult.allErrors
            .map { error ->
                error.defaultMessage!!
            }
            .sorted()

        logger.error("Errors:: $errors")

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(errors.joinToString(", ") {it})
    }

    @ExceptionHandler(InstructorIdNotPresentException::class)
    fun handleInstructorIdNotPresentException(ex: InstructorIdNotPresentException, request: WebRequest): ResponseEntity<Any>{
        logger.error("Exception message: ${ex.message}, $ex")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ex.message)
    }

    @ExceptionHandler(Exception::class)
    fun handleAnyException(ex: Exception, request: WebRequest): ResponseEntity<Any>{
        logger.error("Exception message: ${ex.message}, $ex")
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ex.message)
    }
}