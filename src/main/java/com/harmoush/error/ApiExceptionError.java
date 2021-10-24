package com.harmoush.error;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class ApiExceptionError {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY hh:mm:ss")
    private final LocalDateTime timestamp;
    private String message;
    private String uri;

    public ApiExceptionError() {
        timestamp = LocalDateTime.now();
    }

    public ApiExceptionError(String message, String uri) {
        this();
        this.message = message;
        this.uri = uri;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
