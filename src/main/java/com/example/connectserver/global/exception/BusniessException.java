package com.example.connectserver.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BusniessException extends RuntimeException{
    private final ErrorList errorList;
}
