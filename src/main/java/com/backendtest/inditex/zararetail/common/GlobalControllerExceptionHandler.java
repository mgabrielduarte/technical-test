package com.backendtest.inditex.zararetail.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.webjars.NotFoundException;

@ControllerAdvice
class GlobalControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)  // 404
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{ \"description\": \"Product Not found.\"}");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // 500
    @ExceptionHandler({NullPointerException.class, Exception.class, RuntimeException.class})
    public ResponseEntity handleGenericError() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{ \"description\": \"There was a problem. Please try again later.\"}");
    }
}
