package com.backend.rent.infraestructure.rest.advice;

import com.backend.rent.infraestructure.adapter.exception.PropertyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(PropertyException.class)
    public ResponseEntity<String> handleEmptyInput(PropertyException emptyInputException){
        return new ResponseEntity<String>(emptyInputException.getErrorMessage(), emptyInputException.getErrorCode());
    }

}
