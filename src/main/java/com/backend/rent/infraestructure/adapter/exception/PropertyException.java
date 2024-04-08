package com.backend.rent.infraestructure.adapter.exception;

import org.springframework.http.HttpStatus;

public class PropertyException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private HttpStatus errorCode;
    private String errorMessage;

    private Throwable throwableTrace;

    public PropertyException() {
    }

    public PropertyException(HttpStatus errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public PropertyException(HttpStatus errorCode, String errorMessage, Throwable throwableTrace) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.throwableTrace = throwableTrace;
    }


    public HttpStatus getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(HttpStatus errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
