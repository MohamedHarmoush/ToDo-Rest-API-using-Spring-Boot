package com.harmoush.error;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class ApiBaseError {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY hh:mm:ss")
    private final LocalDateTime timestamp;
    private String uri;

    public ApiBaseError() {
        timestamp = LocalDateTime.now();
    }

    public ApiBaseError(String uri) {
        this();
        this.uri = uri;
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
