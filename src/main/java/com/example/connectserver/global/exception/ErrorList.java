package com.example.connectserver.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;

@Getter
@RequiredArgsConstructor
public enum ErrorList {
    USER_NO_EXIST("존재하지 않는 유저입니다.",HttpStatusCode.valueOf(404));

    private final String message;
    private final HttpStatusCode httpStatusCode;
}
