package com.fspoitmo.fspoitmo.Exceptions;

import org.springframework.http.HttpStatus;

public enum UserExceptionType {

    OBJECT_NOT_FOUND, FORBIDDEN, VALIDATION_ERROR, BAD_REQUEST, SERVER_ERROR;

    Integer getHTTPCode() {
        return getHttpStatus().value();
    }

    String getCode() {
        switch (this) {
            case OBJECT_NOT_FOUND: return "not_found";
            case FORBIDDEN: return "forbidden";
            case VALIDATION_ERROR:
            case BAD_REQUEST: return "validation_error";
            default: return "internal_server_error";
        }
    }

    String getDefaultMessage() {
        switch (this) {
            case OBJECT_NOT_FOUND: return "Object doesn't exist";
            case FORBIDDEN: return "Access is not permitted";
            case BAD_REQUEST: return "Wrong data passed";
            default: return "Unknown error occurred";
        }
    }

    HttpStatus getHttpStatus() {
        switch (this) {
            case OBJECT_NOT_FOUND: return HttpStatus.NOT_FOUND;
            case FORBIDDEN: return HttpStatus.FORBIDDEN;
            case BAD_REQUEST: return HttpStatus.BAD_REQUEST;
            case VALIDATION_ERROR: return HttpStatus.INTERNAL_SERVER_ERROR;
            default: return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}