package com.n26.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GloblaExceptionHandler {

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity handleTransactionExceptions(Exception ex){
        //ex.printStackTrace();
        if(ex.getMessage().equals("404"))
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        else if(ex.getMessage().equals("204"))
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        else if(ex.getMessage().equals("422"))
            return new ResponseEntity<>( HttpStatus.UNPROCESSABLE_ENTITY);
        else
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleHttpMessageNotReadableException(Exception ex){
        //ex.printStackTrace();
        if (ex.getCause() instanceof InvalidFormatException)
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
    }
}
