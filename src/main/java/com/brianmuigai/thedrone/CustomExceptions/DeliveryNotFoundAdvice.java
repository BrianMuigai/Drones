package com.brianmuigai.thedrone.CustomExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DeliveryNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(DeliveryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String deliveryNotFoundHandler(DeliveryNotFoundException ex) {
        return ex.getMessage();
    }
}
