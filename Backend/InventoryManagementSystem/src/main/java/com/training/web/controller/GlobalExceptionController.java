package com.training.web.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import com.training.exceptions.MicroServiceException;
@CrossOrigin(origins = "http://localhost:3003")
@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(MicroServiceException.class)
    public ResponseEntity<ErrorResponse> handleAllException(MicroServiceException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse("ERROR", ex.getMessage(), Collections.emptyList(), Collections.emptyMap()));
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class, BindException.class })
    public ResponseEntity<ErrorResponse> handleValidationException(Exception ex) {
        List<FieldError> fieldErrors;

        if (ex instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
            fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();
        }else {
            fieldErrors = ((BindException) ex).getBindingResult().getFieldErrors();
        }

        List<String> errors = fieldErrors.stream()
        	 .map(MessageSourceResolvable::getDefaultMessage)
        	 .toList();
        Map<String, String> validationErrors = fieldErrors.stream()
            .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (first, second) -> first));

        String message = String.join(", ", errors);

        if (message == null || message.trim().isEmpty()) {
            message = "Validation failed";
        }

        return ResponseEntity.badRequest().body(new ErrorResponse("ERROR", message, errors, validationErrors));
    }

    // ErrorResponse class as an inner class or separate file
    public static class ErrorResponse {
        private String status;
        private String message;
        private List<String> errors;
        private Map<String, String> fieldErrors;

        public ErrorResponse(String status, String message, List<String> errors, Map<String, String> fieldErrors) {
            this.status = status;
            this.message = message;
            this.errors = errors;
            this.fieldErrors = fieldErrors;
        }

        public String getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

        public List<String> getErrors() {
            return errors;
        }

        public Map<String, String> getFieldErrors() {
            return fieldErrors;
        }
    }
}
