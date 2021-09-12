package com.fspoitmo.fspoitmo.Exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResult {
    @JsonProperty("result")
    private String res = null;
    @JsonProperty("error")
    private UserException exError;

    public ErrorResult(UserException exception) {
        exError = exception;
    }
}
