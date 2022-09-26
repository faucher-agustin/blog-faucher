package com.astropay.blogfaucher.exception;

import com.astropay.blogfaucher.utils.ParserUtils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException nfe) {
        Map<String, Object> exceptionBody = new HashMap<>();
        exceptionBody.put("message", nfe.getMessage());
        exceptionBody.put("status" , HttpStatus.NOT_FOUND.toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ParserUtils.mapToJsonString(exceptionBody));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException iae) {
        Map<String, Object> exceptionBody = new HashMap<>();
        exceptionBody.put("message", iae.getMessage());
        exceptionBody.put("status" , HttpStatus.BAD_REQUEST.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ParserUtils.mapToJsonString(exceptionBody));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException cve) {
        Map<String, Object> exceptionBody = new HashMap<>();
        exceptionBody.put("message", cve.getMessage());
        exceptionBody.put("status" , HttpStatus.BAD_REQUEST.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ParserUtils.mapToJsonString(exceptionBody));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingServletRequestParameterException(MissingServletRequestParameterException msrpe) {
        Map<String, Object> exceptionBody = new HashMap<>();
        exceptionBody.put("message", msrpe.getMessage());
        exceptionBody.put("status" , HttpStatus.BAD_REQUEST.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ParserUtils.mapToJsonString(exceptionBody));
    }
}
