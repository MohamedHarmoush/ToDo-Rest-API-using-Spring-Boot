package com.harmoush.error;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends ApiBaseError {

    private List<String> errors;


    public ValidationError() {
        super();
        errors = new ArrayList<>();
    }

    public ValidationError(List<String> errors, String uri) {
        super(uri);
        this.errors = errors;
    }

    public void addError(String error) {
        errors.add(error);
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
