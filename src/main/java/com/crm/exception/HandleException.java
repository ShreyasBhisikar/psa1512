package com.crm.exception;

import com.crm.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice //with this annotation it becomes a global class such that
                  //whenever the exception comes that exception automatically comes here


public class HandleException {
    @ExceptionHandler(Exception.class) //this exceptin.class can handle any kind of exception
    public ResponseEntity
    <ErrorDetails>globalException(ResourceNotFound e, WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(
                e.getMessage(),
                new Date(),
                request.getDescription(true)

            );
        return new ResponseEntity<>( errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}