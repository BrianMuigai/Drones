package com.brianmuigai.thedrone.CustomExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BatteryLowAdvice {

    @ResponseBody
    @ExceptionHandler(BatteryLowException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    String batteryLowHandler(BatteryLowException ex) {
        return ex.getMessage();
    }
}
