package com.misha.application.controller;

import com.misha.application.exception.FileValidationException;
import com.misha.application.exception.ProductValidationException;
import com.misha.application.utils.json.JsonResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


import static com.misha.application.utils.json.JsonUtils.buildJsonResponse;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Created by misha on 18.11.16.
 */
@ControllerAdvice
public class ExceptionHandlerController {

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(FileValidationException.class)
    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    public JsonResponse handleFileValidationEXception(FileValidationException ex) {
        LOG.error("File validation error: {}", ex.getMessage());
        return buildJsonResponse(ex.getMessage());
    }

    @ExceptionHandler(ProductValidationException.class)
    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    public JsonResponse handleProductValidationException(ProductValidationException ex) {
        LOG.error("Product validation error: {}", ex.getMessage());
        ex.getErrors().forEach(LOG::error);
        return buildJsonResponse(ex.getErrors());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    public JsonResponse handleUniqueConstrainException(ConstraintViolationException ex) {
        LOG.error("Constraint violation exception", ex.getMessage());
        return buildJsonResponse("Duplicated product names are not allowed");
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    public JsonResponse handleServerException(Exception ex) {
        LOG.error("Error occurred: {}", ex.getMessage());
        return buildJsonResponse("Error occurred. See log files for detail");
    }
}
