package com.example.reddit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String s) {
        super(s);
    }
}
