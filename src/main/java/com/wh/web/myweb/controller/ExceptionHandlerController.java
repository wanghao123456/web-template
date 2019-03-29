package com.wh.web.myweb.controller;


import com.wh.web.myweb.constants.ResultConstant;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public Object handler(Exception e) {
        if (e instanceof BadCredentialsException) {
            return ResultConstant.LOGIN_EXCEPTION;
        }
        return ResultConstant.EXCEPTION;
    }
}
