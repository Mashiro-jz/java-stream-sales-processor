package pl.mashiro_jz.salesanalyzer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;


@RestControllerAdvice
public class GlobalExceptionHandler {

    public record ApiError(String message, int status, String timestamp) {}

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(MethodArgumentTypeMismatchException ex){
        String customName = "An invalid data format was provided for the parameter: " + ex.getName();

        ApiError errorBody = new ApiError(customName, HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }
}
