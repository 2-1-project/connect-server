package com.example.connectserver.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHendler {
    @ExceptionHandler(BusniessException.class)
    public ResponseEntity BusniessHendler(BusniessException ex) {
        ErrorList err = ex.getErrorList();
        return new ResponseEntity<>(err.getHttpStatusCode());
    }
}
