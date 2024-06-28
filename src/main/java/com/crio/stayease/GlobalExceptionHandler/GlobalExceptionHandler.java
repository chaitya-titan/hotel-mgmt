package com.crio.stayease.GlobalExceptionHandler;

import com.crio.stayease.exceptions.DifferentApiForRegistrationException;
import com.crio.stayease.exceptions.PasswordMismatchException;
import com.crio.stayease.exceptions.UserAlreadyExistsException;
import com.crio.stayease.exceptions.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return ResponseEntity.status(409).body(e.getMessage());
    }
    @ExceptionHandler(value = DifferentApiForRegistrationException.class)
    public ResponseEntity<String> handleDifferentApiForAdminRegistrationException(DifferentApiForRegistrationException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }
    @ExceptionHandler(value = PasswordMismatchException.class)
    public ResponseEntity<String> handlePasswordMismatchException(PasswordMismatchException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
}

