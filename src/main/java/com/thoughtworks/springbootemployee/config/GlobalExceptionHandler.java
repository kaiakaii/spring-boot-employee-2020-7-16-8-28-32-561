package com.thoughtworks.springbootemployee.config;

import com.thoughtworks.springbootemployee.exception.NotFoundIDException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = NotFoundIDException.class)
    public String notFoundExceptionHandler(NotFoundIDException notFoundExceptionHandler) {
        return notFoundExceptionHandler.getMessage();
    }
}
