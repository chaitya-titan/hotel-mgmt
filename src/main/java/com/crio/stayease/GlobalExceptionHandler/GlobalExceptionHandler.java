package com.crio.stayease.GlobalExceptionHandler;

import com.crio.stayease.exceptions.*;
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
    @ExceptionHandler(value = UnAuthorisedException.class)
    public ResponseEntity<String> handleUnAuthorisedException(UnAuthorisedException e) {
        return ResponseEntity.status(401).body(e.getMessage());
    }
    @ExceptionHandler(value = HotelNotFoundException.class)
    public ResponseEntity<String> handleHotelNotFoundException(HotelNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }
    @ExceptionHandler(value = RoomsUnavailableException.class)
    public ResponseEntity<String> handleRoomNotFoundException(RoomsUnavailableException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }
}

