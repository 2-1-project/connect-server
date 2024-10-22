package com.example.connectserver.domain.user.exception;

import com.example.connectserver.global.exception.BusniessException;
import com.example.connectserver.global.exception.ErrorList;

public class OAuthUserNoExistException extends BusniessException {
    OAuthUserNoExistException exception = new OAuthUserNoExistException();
    public OAuthUserNoExistException() {
        super(ErrorList.USER_NO_EXIST);
    }
}
