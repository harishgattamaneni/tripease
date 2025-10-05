package com.example.tripease;

import com.example.tripease.Exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CabNotFoundException.class)
    public ResponseEntity<String> CabNotFoundException(CabNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> CustomerNotFoundException(CustomerNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DriverNotFoundException.class)
    public ResponseEntity<String> DriverNotFoundException(DriverNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(tripAlreadyEnded.class)
    public ResponseEntity<String> tripAlreadyEnded(tripAlreadyEnded ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(tripNotEnded.class)
    public ResponseEntity<String> tripNotEnded(tripNotEnded ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> validationException(MethodArgumentNotValidException ex){

        // Extract all user-friendly error messages from the binding result
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage()) // e.g., "name: Driver name is required"
                .collect(Collectors.toList());

        // A status of BAD_REQUEST (400) is more appropriate for validation failures than NOT_FOUND (404)
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleJsonReadException(HttpMessageNotReadableException ex) {
        String message = ex.getMessage();

        // Simple check: Look for the Enum class name in the error message
        if (message != null && message.contains("Enum.Gender")) {
            return new ResponseEntity<>("Gender must be one of MALE, FEMALE, or OTHER.", HttpStatus.BAD_REQUEST);
        }

        // Handle other JSON parsing errors generically
        return new ResponseEntity<>("Invalid request format: " + ex.getRootCause().getMessage(), HttpStatus.BAD_REQUEST);
    }
}