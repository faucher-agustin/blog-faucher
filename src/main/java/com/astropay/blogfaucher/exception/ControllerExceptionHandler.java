package com.astropay.blogfaucher.exception;

import com.astropay.blogfaucher.utils.ParserUtils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException nfe) {
        Map<String, Object> exceptionBody = new HashMap<>();
        exceptionBody.put("message", nfe.getMessage());
        exceptionBody.put("status" , HttpStatus.NOT_FOUND.toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ParserUtils.toJsonString(exceptionBody));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException iae) {
        Map<String, Object> exceptionBody = new HashMap<>();
        exceptionBody.put("message", iae.getMessage());
        exceptionBody.put("status" , HttpStatus.BAD_REQUEST.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ParserUtils.toJsonString(exceptionBody));
    }
}
