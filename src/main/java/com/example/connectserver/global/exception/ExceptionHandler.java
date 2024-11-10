package com.example.connectserver.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(BusinessException.class)
    public ResponseEntity BusinessHandler(BusinessException ex) {
        ErrorList err = ex.getErrorList();
        return new ResponseEntity<>(err.getHttpStatusCode());
    }
}
