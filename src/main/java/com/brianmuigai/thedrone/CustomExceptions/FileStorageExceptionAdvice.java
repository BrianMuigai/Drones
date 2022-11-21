package com.brianmuigai.thedrone.CustomExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class FileStorageExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(FileStorageException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String fileExceptionHandler(FileStorageException ex) {
        return ex.getMessage();
    }
}
