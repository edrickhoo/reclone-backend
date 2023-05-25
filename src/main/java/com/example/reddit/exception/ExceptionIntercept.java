package com.example.reddit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionIntercept {

    @ExceptionHandler(SubredditNotFoundException.class)
    public ResponseEntity<ExceptionResponse> SubredditNotFoundHandler (SubredditNotFoundException ex) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .httpCode(HttpStatus.NOT_FOUND)
                .time(Instant.now().getEpochSecond())
                .error(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ExceptionResponse> PostNotFoundExceptionHandler (PostNotFoundException ex) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .httpCode(HttpStatus.NOT_FOUND)
                .time(Instant.now().getEpochSecond())
                .error(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMap);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> RuntimeExceptionHandler (RuntimeException ex) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .httpCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .time(Instant.now().getEpochSecond())
                .error(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }

    @ExceptionHandler(SpringRedditException.class)
    public ResponseEntity<ExceptionResponse> RuntimeExceptionHandler (SpringRedditException ex) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .httpCode(HttpStatus.BAD_REQUEST)
                .time(Instant.now().getEpochSecond())
                .error(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }


}
