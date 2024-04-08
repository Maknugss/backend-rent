package com.backend.rent.infraestructure.rest.response;

import java.util.Map;

public class ErrorResponseMap {

    Map<String, String> message;
    private int statusCode;

    public ErrorResponseMap(Map<String, String> message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public Map<String, String> getMessage() {
        return message;
    }

    public void setMessage(Map<String, String> message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
