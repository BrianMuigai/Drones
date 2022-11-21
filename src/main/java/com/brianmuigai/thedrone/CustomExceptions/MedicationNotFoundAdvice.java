package com.brianmuigai.thedrone.CustomExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MedicationNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(MedicationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String medicationNotFoundHandler(MedicationNotFoundException ex) {
        return ex.getMessage();
    }
}
