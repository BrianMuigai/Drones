package com.brianmuigai.thedrone.CustomExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DroneNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(DroneNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String droneNotFoundHandler(DroneNotFoundException ex) {
        return ex.getMessage();
    }
}
