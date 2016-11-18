package com.misha.application.exception;

import java.util.List;

/**
 * Created by misha on 18.11.16.
 */
public class ProductValidationException extends RuntimeException {

    private List<String> errors;

    public ProductValidationException(List<String> errors) {
        super("Product is not valid");
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }


}
